// Copyright 2006, 2007 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry.ioc;

import static org.apache.tapestry.ioc.IOCConstants.MODULE_BUILDER_MANIFEST_ENTRY_NAME;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Manifest;

/**
 * A collection of utility methods for a couple of different areas, including creating the initial
 * {@link org.apache.tapestry.ioc.Registry}.
 */
public final class IOCUtilities
{
    private IOCUtilities()
    {
    }

    /**
     * Construct a default registry, including modules identify via the Tapestry-Module-Classes
     * Manifest entry.
     * 
     * @return constructed Registry, after eager loading of services
     */
    public static Registry buildDefaultRegistry()
    {
        RegistryBuilder builder = new RegistryBuilder();

        addDefaultModules(builder);

        Registry registry = builder.build();

        registry.performRegistryStartup();

        return registry;
    }

    /**
     * Scans the classpath for JAR Manifests that contain the Tapestry-Module-Classes attribute and
     * adds each corresponding class to the RegistryBuilder.
     * 
     * @param builder
     *            the builder to which modules will be added
     */
    public static void addDefaultModules(RegistryBuilder builder)
    {
        try
        {
            Enumeration<URL> urls = builder.getClassLoader().getResources("META-INF/MANIFEST.MF");

            while (urls.hasMoreElements())
            {
                URL url = urls.nextElement();

                addModulesInManifest(builder, url);
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static void addModulesInManifest(RegistryBuilder builder, URL url) throws IOException
    {
        InputStream in = null;

        try
        {
            in = url.openStream();

            Manifest mf = new Manifest(in);

            in.close();

            in = null;

            addModulesInManifest(builder, mf);
        }
        finally
        {
            close(in);
        }
    }

    static void addModulesInManifest(RegistryBuilder builder, Manifest mf)
    {
        String list = mf.getMainAttributes().getValue(MODULE_BUILDER_MANIFEST_ENTRY_NAME);

        if (list == null) return;

        String[] classnames = list.split(",");

        for (String classname : classnames)
        {
            builder.add(classname.trim());
        }
    }

    /**
     * Closes an input stream (or other Closeable), ignoring any exception.
     * 
     * @param closeable
     *            the thing to close, or null to close nothing
     */
    private static void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (IOException ex)
            {
                // Ignore.
            }
        }
    }
}
