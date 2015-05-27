package domainapp.dom.modules.petclinic;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Predicate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class VisitContributions {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Visit> visits(Pet pet){
        return container.allMatches(Visit.class, new Predicate<Visit>() {
            @Override public boolean apply(final Visit input) {
                return input.getPet().equals(pet);
            }
        });
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Visit> visits(PetOwner owner){
        return container.allMatches(Visit.class, new Predicate<Visit>() {
            @Override public boolean apply(final Visit input) {
                return input.getPet().getPetOwner().equals(owner);
            }
        });
    }

    @Inject
    DomainObjectContainer container;

    @Inject
    ClockService clockService;

}
