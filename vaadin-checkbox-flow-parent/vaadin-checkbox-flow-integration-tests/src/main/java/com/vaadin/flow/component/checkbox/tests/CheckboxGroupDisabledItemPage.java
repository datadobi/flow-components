/*
 * Copyright 2000-2024 Vaadin Ltd.
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
package com.vaadin.flow.component.checkbox.tests;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * View for {@link CheckboxGroup} integration tests.
 *
 * @author Vaadin Ltd
 */
@Route("vaadin-checkbox-group-disabled-item")
public class CheckboxGroupDisabledItemPage extends VerticalLayout {

    public CheckboxGroupDisabledItemPage() {
        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setItems("foo", "bar", "baz");
        group.select("bar");
        group.setItemEnabledProvider(item -> !"bar".equals(item));
        group.setId("checkbox-group-disabled-item");

        NativeButton toggleBarButton = new NativeButton("Toggle \"bar\"",
                event -> {
                    boolean isBarSelected = group.isSelected("bar");
                    if (isBarSelected) {
                        group.deselect("bar");
                    } else {
                        group.select("bar");
                    }
                });
        toggleBarButton.setId("toggle-bar-button");

        NativeButton toggleEnabledButton = new NativeButton("Toggle enabled",
                event -> group.setEnabled(!group.isEnabled()));
        toggleEnabledButton.setId("toggle-enabled-button");

        add(group, new Div(toggleBarButton, toggleEnabledButton));
    }
}
