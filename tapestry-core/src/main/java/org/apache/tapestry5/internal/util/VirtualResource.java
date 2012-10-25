// Copyright 2012 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.internal.util;

import org.apache.tapestry5.ioc.Resource;

import java.net.URL;
import java.util.Locale;

/**
 * Base class for virtual resources: resources that are not simply mapped to stored files, but are assembled, as necessary,
 * on the fly. This is used inside Tapestry to expose the application's localized message catalog as a module.
 * Subclasses should implement the {@link org.apache.tapestry5.ioc.Resource#openStream()} method to return a stream of
 * the contents of the virtual resource.
 *
 * @see org.apache.tapestry5.services.javascript.ModuleManager
 * @see org.apache.tapestry5.internal.services.javascript.ModuleAssetRequestHandler
 * @since 5.4
 */
public abstract class VirtualResource implements Resource
{
    private <T> T unsupported(String name)
    {
        throw new UnsupportedOperationException(String.format("Method %s() is not supported for a VirtualResource.", name));
    }

    @Override
    public boolean exists()
    {

        return true;
    }

    @Override
    public URL toURL()
    {
        return unsupported("toURL");
    }

    @Override
    public Resource forLocale(Locale locale)
    {
        return unsupported("forLocale");
    }

    @Override
    public Resource forFile(String relativePath)
    {
        return unsupported("forFile");
    }

    @Override
    public Resource withExtension(String extension)
    {
        return unsupported("withExtension");
    }

    @Override
    public String getFolder()
    {
        return unsupported("getFolder");
    }

    @Override
    public String getFile()
    {
        return unsupported("getFile");
    }

    @Override
    public String getPath()
    {
        return unsupported("getPath");
    }
}