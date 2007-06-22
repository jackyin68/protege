package org.protege.editor.owl.ui.view;

import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
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
 * Medical Informatics Group<br>
 * Date: 07-May-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public abstract class AbstractOWLIndividualViewComponent extends AbstractOWLSelectionViewComponent {

    protected boolean isOWLIndividualView() {
        return true;
    }


    final public void initialiseView() throws Exception {
        initialiseIndividualsView();
    }


    public OWLIndividual getSelectedOWLIndividual() {
        return getOWLWorkspace().getOWLSelectionModel().getLastSelectedIndividual();
    }


    final protected OWLEntity updateView() {
        OWLIndividual selIndividual = updateView(getSelectedOWLIndividual());
        if (selIndividual != null) {
            updateRegisteredActions();
        }
        else {
            disableRegisteredActions();
        }
        return selIndividual;
    }


    protected abstract OWLIndividual updateView(OWLIndividual selelectedIndividual);


    public abstract void initialiseIndividualsView() throws Exception;
}
