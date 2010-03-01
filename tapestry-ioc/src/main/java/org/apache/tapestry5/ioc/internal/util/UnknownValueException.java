// Copyright 2010 The Apache Software Foundation
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

package org.apache.tapestry5.ioc.internal.util;

/**
 * Special exception used when a value (typically from a map) is referenced that does not exist. Uses a
 * {@link PossibleValues} object
 * to track what the known values are.
 * 
 * @since 5.2.0
 */
public class UnknownValueException extends TapestryException
{
    private static final long serialVersionUID = -8131503136299055706L;

    private final PossibleValues possibleValues;

    public UnknownValueException(String message, PossibleValues values)
    {
        this(message, null, null, values);
    }

    public UnknownValueException(String message, Object location, Throwable cause, PossibleValues values)
    {
        super(message, location, cause);

        this.possibleValues = values;
    }

    public PossibleValues getPossibleValues()
    {
        return possibleValues;
    }

}
