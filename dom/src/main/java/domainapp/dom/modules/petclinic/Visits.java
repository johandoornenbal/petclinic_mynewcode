package domainapp.dom.modules.petclinic;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService
public class Visits {

    public String getId() {
        return "visit";
    }

    public String iconName() {
        return "Visit";
    }


    @MemberOrder(sequence = "1")
    @Action(semantics = SemanticsOf.SAFE)
    public List<Visit> allVisits() {
        return container.allInstances(Visit.class);
    }

    @MemberOrder(sequence = "2")
    public Visit addVisit(
            final Pet pet,
            final @ParameterLayout(named = "Notes", multiLine = 3) String notes) {
        final Visit obj = container.newTransientInstance(Visit.class);
        obj.setPet(pet);
        obj.setCheckInTime(DateTime.now()) ;
        obj.setNotes(notes);
        container.persistIfNotAlready(obj);
        return obj;
    }

    @javax.inject.Inject
    DomainObjectContainer container;

    @Inject
    ClockService clockService;

}
