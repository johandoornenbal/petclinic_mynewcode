/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.integtests.tests.modules.petclinic;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;

import domainapp.dom.modules.petclinic.Pet;
import domainapp.fixture.scenarios.RecreatePets;
import domainapp.integtests.tests.PetClinicAppIntegTest;
import static org.assertj.core.api.Assertions.assertThat;

public class PetIntegTest extends PetClinicAppIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    RecreatePets fs;
    Pet petPojo;
    Pet petWrapped;

    @Before
    public void setUp() throws Exception {
        // given
        fs = new RecreatePets().setNumber(1);
        fixtureScripts.runFixtureScript(fs, null);

        petPojo = fs.getPets().get(0);

        assertThat(petPojo).isNotNull();
        petWrapped = wrap(petPojo);
    }

    public static class Name extends PetIntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String name = petWrapped.getName();
            // then
            assertThat(name).isEqualTo(fs.NAMES.get(0));
        }

        @Test
        public void cannotBeUpdatedDirectly() throws Exception {

            // expect
            expectedExceptions.expect(DisabledException.class);

            // when
            petWrapped.setName("new name");
        }
    }

    public static class UpdateName extends PetIntegTest {

        @Test
        public void happyCase() throws Exception {

            // when
            petWrapped.updateName("new name");

            // then
            assertThat(petWrapped.getName()).isEqualTo("new name");
        }

        @Test
        public void failsValidation() throws Exception {

            // expect
            expectedExceptions.expect(InvalidException.class);
            expectedExceptions.expectMessage("Exclamation mark is not allowed");

            // when
            petWrapped.updateName("new name!");
        }
    }


    public static class Title extends PetIntegTest {

        @Inject
        DomainObjectContainer container;

        @Test
        public void interpolatesName() throws Exception {

            // given
            final String name = petWrapped.getName();

            // when
            final String title = container.titleOf(petWrapped);

            // then
            assertThat(title).isEqualTo("Object: " + name);
        }
    }
}