// Copyright 2006, 2007, 2008 The Apache Software Foundation
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

package org.apache.tapestry.corelib.pages;

import org.apache.tapestry.TapestryConstants;
import org.apache.tapestry.annotations.ContentType;
import org.apache.tapestry.annotations.Property;
import org.apache.tapestry.ioc.annotations.Inject;
import org.apache.tapestry.ioc.annotations.Symbol;
import org.apache.tapestry.ioc.internal.util.InternalUtils;
import org.apache.tapestry.services.ExceptionReporter;
import org.apache.tapestry.services.Request;
import org.apache.tapestry.services.Session;

import java.util.List;

/**
 * Responsible for reporting runtime exceptions. This page is quite verbose and is usually overridden in a production
 * application. When {@link TapestryConstants#PRODUCTION_MODE_SYMBOL} is "true", it is very abbreviated.
 *
 * @see org.apache.tapestry.corelib.components.ExceptionDisplay
 */
@ContentType("text/html")
public class ExceptionReport implements ExceptionReporter
{
    private static final String PATH_SEPARATOR_PROPERTY = "path.separator";

    @Property
    private String _attributeName;

    @Inject
    @Property
    private Request _request;

    @Inject
    @Symbol(TapestryConstants.PRODUCTION_MODE_SYMBOL)
    @Property(write = false)
    private boolean _productionMode;

    @Inject
    @Symbol(TapestryConstants.TAPESTRY_VERSION_SYMBOL)
    @Property(write = false)
    private String _tapestryVersion;

    @Property(write = false)
    private Throwable _rootException;

    @Property
    private String _propertyName;
    private final String _pathSeparator = System.getProperty(PATH_SEPARATOR_PROPERTY);

    public void reportException(Throwable exception)
    {
        _rootException = exception;
    }

    public boolean getHasSession()
    {
        return _request.getSession(false) != null;
    }

    public Session getSession()
    {
        return _request.getSession(false);
    }

    public Object getAttributeValue()
    {
        return getSession().getAttribute(_attributeName);
    }

    /**
     * Returns a <em>sorted</em> list of system property names.
     */
    public List<String> getSystemProperties()
    {
        return InternalUtils.sortedKeys(System.getProperties());
    }

    public String getPropertyValue()
    {
        return System.getProperty(_propertyName);
    }

    public boolean isSimpleProperty()
    {
        if (_propertyName.equals(PATH_SEPARATOR_PROPERTY)) return true;

        return !getPropertyValue().contains(_pathSeparator);
    }

    public String[] getComplexPropertyValue()
    {
        // Neither : nor ; is a regexp character.

        return getPropertyValue().split(_pathSeparator);
    }

}
