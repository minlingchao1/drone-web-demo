package com.neunn;/*
 * Copyright 2016 Compass Team.
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing,
 * Software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DroneTest {

    private Drone subject;

    @Before
    public void setup() {
        subject = new Drone();
    }

    @Test
    public void testGetMessage() {
        assertEquals("Hello World!", subject.getMessage(false));
    }

}
