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

package domainapp.fixture.modules.petclinic;

import domainapp.dom.modules.petclinic.Pet;
import domainapp.dom.modules.petclinic.PetSpecies;
import domainapp.dom.modules.petclinic.Pets;
import domainapp.fixture.PetClinicAbstractFixture;

public class PetFixture extends PetClinicAbstractFixture {

    public PetFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override protected void execute(final ExecutionContext ec) {

        deleteFrom(Pet.class);

        create(ec, "Bello", PetSpecies.DOG);
        create(ec, "Hector", PetSpecies.DOG);
        create(ec, "Tweety", PetSpecies.BIRD);

    }

    private void create(final ExecutionContext ec, final String name, final PetSpecies species) {
        ec.addResult(this, pets.create(name, species));
    }

    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private Pets pets;

}
