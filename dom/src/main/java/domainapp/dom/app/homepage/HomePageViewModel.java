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
package domainapp.dom.app.homepage;

import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.base.Predicate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.modules.petclinic.Pet;
import domainapp.dom.modules.petclinic.PetOwner;
import domainapp.dom.modules.petclinic.PetOwners;
import domainapp.dom.modules.petclinic.PetSpecies;
import domainapp.dom.modules.petclinic.Pets;
import domainapp.dom.modules.petclinic.Visit;
import domainapp.dom.modules.petclinic.Visits;

@ViewModel
public class HomePageViewModel {

    public HomePageViewModel() {
    }

    public String title() {
        return "Homepage";
    }

    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Visit> getActiveVisits() {
        return container.allMatches(Visit.class, (Predicate<Visit>) input -> input.getCheckOutTime() == null);
    }

    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Visit> getInactiveVisits() {
        return container.allMatches(Visit.class, (Predicate<Visit>) input -> input.getCheckOutTime() != null);
    }

    public HomePageViewModel visitNewPetAndOwner(
            final @ParameterLayout(named = "Pet Name") String petName,
            final @ParameterLayout(named = "Pet Species") PetSpecies species,
            final @ParameterLayout(named = "Owner First Name") String ownerFirstName,
            final @ParameterLayout(named = "Owner Last Name") String ownerLastName,
            final @ParameterLayout(named = "Notes", multiLine = 3) String notes
    ) {
        visits.addVisit(pets.create(petName, species, petOwners.addOwner(ownerFirstName, ownerLastName)), notes);
        return this;
    }

    public HomePageViewModel visitForExistingPet(
            final @ParameterLayout(named = "Pet") Pet pet,
            final @ParameterLayout(named = "Notes", multiLine = 3) String notes
    ) {
        visits.addVisit(pet, notes);
        return this;
    }

    public HomePageViewModel visitForExistingOwner(
            final @ParameterLayout(named = "Owner") PetOwner owner,
            final @ParameterLayout(named = "Pet") Pet pet,
            final @ParameterLayout(multiLine = 3) String notes
    ) {
        visits.addVisit(pet, notes);
        return this;
    }

    public SortedSet<Pet> choices1VisitForExistingOwner(
            final PetOwner owner,
            final Pet pet,
            final String notes
    ) {
        return owner == null ? null : owner.getPets();
    }

    @Inject
    private Visits visits;

    @Inject
    private Pets pets;

    @Inject
    private PetOwners petOwners;

    @Inject
    private DomainObjectContainer container;
}
