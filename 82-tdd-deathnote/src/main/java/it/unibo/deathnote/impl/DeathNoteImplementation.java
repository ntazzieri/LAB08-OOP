package it.unibo.deathnote.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

/**
 * An implementation of DeathNote
 */
public class DeathNoteImplementation implements DeathNote {

    private static final long D_DETAILS_TIME_LIMIT = 6040L;
    private static final long D_CAUSE_TIME_LIMIT = 40L;
    private final List<PersonToKill> names = new ArrayList<>();

    @Override
    public String getRule(final int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber >= DeathNote.RULES.size()) {
            throw new IllegalArgumentException("ruleNumber " + ruleNumber + " does not exist");
        }
        return DeathNote.RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        if(Objects.isNull(name)) {
            throw new NullPointerException("The given name is null");
        }
        if(!name.isEmpty() && !name.isBlank()) {
            this.names.add(new PersonToKill(name));
        }
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if(names.isEmpty()) {
            throw new IllegalStateException("There's no name written in this deathNote");
        } else if(Objects.isNull(cause)) {
            throw new IllegalStateException("The cause is NULL");
        }
        if(hasTimePassed(names.getLast().writingTime, D_CAUSE_TIME_LIMIT)) {
            return false;
        }
        names.getLast().deathCause = cause;
        return true;
    }

    @Override
    public boolean writeDetails(final String details) {
        if(names.isEmpty()) {
            throw new IllegalStateException("There's no name written in this deathNote");
        } else if (Objects.isNull(details)) {
            throw new IllegalStateException("The details are null");
        }
        if(hasTimePassed(names.getLast().writingTime, D_DETAILS_TIME_LIMIT)) {
            return false;
        }
        names.getLast().deathDetails = details;
        return true;
    }

    @Override
    public String getDeathCause(final String name) {
        if(!isNameWritten(name)) {
            throw new IllegalArgumentException(name + " is not written in this death note");
        }
        return getPersonToKill(name).deathCause;
    }

    @Override
    public String getDeathDetails(final String name) {
        if(!isNameWritten(name)) {
            throw new IllegalArgumentException(name + " is not written in this death note");
        }
        return getPersonToKill(name).deathDetails;
    }

    @Override
    public boolean isNameWritten(final String name) {
        return Objects.nonNull(getPersonToKill(name));
    }

    private PersonToKill getPersonToKill(final String name) {
        for(final PersonToKill person : names) {
            if(person.name == name) {
                return person;
            }
        }
        return null;
    }

    private boolean hasTimePassed(final long oldTime, final long timePassed) {
        return System.currentTimeMillis() - oldTime > timePassed;
    }

    private static class PersonToKill {
        private final String name;
        private final long writingTime;
        private String deathCause;
        private String deathDetails;
        
        PersonToKill(final String name) {
            this.name = name;
            writingTime = System.currentTimeMillis();
            deathCause = "heart attack";
            deathDetails = "";
        }
    }

}