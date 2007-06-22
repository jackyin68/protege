package org.protege.editor.owl.model.hierarchy;

import org.semanticweb.owl.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-May-2007<br><br>
 */
public class IndividualsByTypeHierarchyProvider extends AbstractOWLObjectHierarchyProvider<OWLObject> {

    private Set<OWLObject> roots;

    private Set<OWLOntology> ontologies;


    public IndividualsByTypeHierarchyProvider(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
        this.roots = new HashSet<OWLObject>();
        this.ontologies = new HashSet<OWLOntology>();
    }


    public void setOntologies(Set<OWLOntology> ontologies) {
        this.ontologies.clear();
        this.ontologies.addAll(ontologies);
        rebuild();
    }


    private void rebuild() {
        for (OWLOntology ont : ontologies) {
            for (OWLIndividual ind : ont.getReferencedIndividuals()) {
                for (OWLClassAssertionAxiom ax : ont.getClassAssertionAxioms(ind)) {
                    if (!ax.getDescription().isAnonymous()) {
                        roots.add(ax.getDescription());
                    }
                }
            }
        }
        fireHierarchyChanged();
    }


    public Set<OWLObject> getRoots() {
        return roots;
    }


    public Set<OWLObject> getChildren(OWLObject object) {
        if (roots.contains(object)) {
            OWLClass cls = (OWLClass) object;
            Set<OWLObject> individuals = new HashSet<OWLObject>();
            for (OWLOntology ont : ontologies) {
                for (OWLClassAssertionAxiom ax : ont.getClassAssertionAxioms(cls)) {
                    individuals.add(ax.getIndividual());
                }
            }
            return individuals;
        }
        else {
            return Collections.emptySet();
        }
    }


    public Set<OWLObject> getParents(OWLObject object) {
        if (roots.contains(object)) {
            return Collections.emptySet();
        }
        else {
            OWLIndividual ind = (OWLIndividual) object;
            Set<OWLObject> clses = new HashSet<OWLObject>();
            for (OWLOntology ont : ontologies) {
                for (OWLClassAssertionAxiom ax : ont.getClassAssertionAxioms(ind)) {
                    if (!ax.getDescription().isAnonymous()) {
                        clses.add(ax.getDescription().asOWLClass());
                    }
                }
            }
            return clses;
        }
    }


    public Set<OWLObject> getEquivalents(OWLObject object) {
        return Collections.emptySet();
    }


    public boolean containsReference(OWLObject object) {
        return true;
    }
}
