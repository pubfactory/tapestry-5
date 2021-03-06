//  Copyright 2008, 2010 The Apache Software Foundation
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

package org.apache.tapestry5.ioc.internal.services;

import org.apache.tapestry5.commons.AnnotationProvider;
import org.apache.tapestry5.commons.ObjectLocator;
import org.apache.tapestry5.commons.ObjectProvider;
import org.apache.tapestry5.ioc.annotations.Autobuild;

/**
 * Checks for the {@link org.apache.tapestry5.ioc.annotations.Autobuild} annotation and, if so
 * invokes {@link org.apache.tapestry5.commons.ObjectLocator#autobuild(Class)} on it.
 */
public class AutobuildObjectProvider implements ObjectProvider
{
    @Override
    public <T> T provide(Class<T> objectType, AnnotationProvider annotationProvider,
            ObjectLocator locator)
    {
        Autobuild annotation = annotationProvider.getAnnotation(Autobuild.class);

        if (annotation != null)
            return locator
                    .autobuild("Autobuilding instance of " + objectType.getName(), objectType);

        return null;
    }
}
