/*
 * Copyright 2000-2018 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.textfield.testbench;

import com.vaadin.testbench.HasLabel;
import com.vaadin.testbench.HasPlaceholder;
import com.vaadin.testbench.HasStringValueProperty;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

import java.util.Collections;
import java.util.Map;

/**
 * A TestBench element representing a <code>&lt;vaadin-text-area&gt;</code>
 * element.
 */
@Element("vaadin-text-area")
public class TextAreaElement extends TestBenchElement
        implements HasStringValueProperty, HasLabel, HasPlaceholder {
    @Override
    public void setValue(String string) {
        HasStringValueProperty.super.setValue(string);
        dispatchEvent("change",
                Collections.singletonMap("bubbles", true));
        dispatchEvent("blur");
    }

}
