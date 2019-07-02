package org.mplywacz.demo.model;
/*
Author: BeGieU
Date: 02.07.2019
*/

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Driver extends BasicEntity {

    private Set<Transit> transits = new HashSet<>();

    public Set<Transit> getTransits() {
        return transits;
    }

    public void setTransits(Set<Transit> transits) {
        this.transits = transits;
    }
}
