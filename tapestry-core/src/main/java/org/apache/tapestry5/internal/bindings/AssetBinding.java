// Copyright 2009, 2020 The Apache Software Foundation
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

package org.apache.tapestry5.internal.bindings;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.commons.Location;

public class AssetBinding extends AbstractBinding
{
    private final String description;

    private final Asset asset;

    public AssetBinding(Location location, String description, Asset asset)
    {
        super(location);

        this.description = description;
        this.asset = asset;
    }

    @Override
    public Class getBindingType()
    {
        return Asset.class;
    }

    public Object get()
    {
        return asset;
    }

    @Override
    public String toString()
    {
        return String.format("AssetBinding[%s: %s]", description, asset);
    }
}
