// Copyright 2009, 2010 The Apache Software Foundation
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

package org.apache.tapestry5.internal.services;

import org.apache.tapestry5.commons.services.TypeCoercer;
import org.apache.tapestry5.internal.AbstractEventContext;

/**
 * Simple implementation of {@link org.apache.tapestry5.EventContext}.
 * 
 * @since 5.1.0.0
 */
public class ArrayEventContext extends AbstractEventContext
{
    private final TypeCoercer typeCoercer;

    private final Object[] values;

    public ArrayEventContext(TypeCoercer typeCoercer, Object... values)
    {
        this.typeCoercer = typeCoercer;
        this.values = values;
    }

    public <T> T get(Class<T> desiredType, int index)
    {
        return typeCoercer.coerce(values[index], desiredType);
    }

    public int getCount()
    {
        return values.length;
    }
}
