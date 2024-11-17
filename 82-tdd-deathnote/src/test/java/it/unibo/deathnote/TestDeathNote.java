package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.*;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

class TestDeathNote {
    private static final String DEATH_DETAILS = "ran for too long";
    private static final String STD_CAUSE_OF_DEATH = "heart attack";
    private static final String CAUSE_OF_DEATH = "karting accident";
    private static final String HUMAN_NOT_TO_KILL = "Nicolas";
    private static final String HUMAN = "Andrea";
    private DeathNote deathNote;

    @BeforeEach
    void setUp() {
        deathNote = new DeathNoteImplementation();
    }

    /*
     * Rule number 0 and negative rules do not exist in the DeathNote rules.
     * - check that the exceptions are thrown correctly, that their type is the expected one, and that the message is not null, empty, or blank.
     */
    @Test
    void testNonNegativeOrZeroRuleGetter() {
        try{
            deathNote.getRule(0);
            fail("Rule number 0 is possible");
        }catch(IllegalArgumentException e) { 
            checkEmptyString(e.getMessage());
        }
        try {
            deathNote.getRule(-10);
            fail("Negative rule is possible");
        } catch (IllegalArgumentException e) {
            checkEmptyString(e.getMessage());
        }
    }

    /*
     * No rule is empty or null in the DeathNote rules.
     * - for all the valid rules, check that none is null or blank
     */
    @Test
    void testNotEmptyOrNullRule() {
        for (String rule : DeathNote.RULES) {
            checkEmptyString(rule);
        }
    }

    /**
     * The human whose name is written in the DeathNote will eventually die.
     * -verify that the human has not been written in the notebook yet
     * -write the human in the notebook
     * -verify that the human has been written in the notebook
     * -verify that another human has not been written in the notebook
     * -verify that the empty string has not been written in the notebook
     */
    @Test
    void testWritingInDeathNote() {
        assertFalse(deathNote.isNameWritten(HUMAN));
        deathNote.writeName(HUMAN);
        assertTrue(deathNote.isNameWritten(HUMAN));
        assertFalse(deathNote.isNameWritten(HUMAN_NOT_TO_KILL));
        deathNote.writeName("");
        assertFalse(deathNote.isNameWritten(""));
        try {
            deathNote.writeName(null);
            fail("A null name is writable");
        } catch (NullPointerException e) {
            checkEmptyString(e.getMessage());
        }
    }


    /*
     * If the cause of death is written within the next 40 milliseconds of writing the person's name, it will happen.
      * - check that writing a cause of death before writing a name throws the correct exception
      * - write the name of a human in the notebook
      * - verify that the cause of death is a heart attack
      * - write the name of another human in the notebook
      * - set the cause of death to "karting accident"
      * - verify that the cause of death has been set correctly (returned true, and the cause is indeed "karting accident")
      * - sleep for 100ms
      * - try to change the cause of death 
      * - verify that the cause of death has not been changed
     */
    @Test
    void testKillProcess() throws InterruptedException {
        try {
            deathNote.writeDeathCause(CAUSE_OF_DEATH);
            fail("writeDeathCause() permits to write a death cause before writing a name");
        } catch (IllegalStateException e) {
            checkEmptyString(e.getMessage());
        }
        deathNote.writeName(HUMAN);
        assertEquals(STD_CAUSE_OF_DEATH, deathNote.getDeathCause(HUMAN));
        assertTrue(deathNote.writeDeathCause(CAUSE_OF_DEATH));
        assertEquals(CAUSE_OF_DEATH, deathNote.getDeathCause(HUMAN));
        Thread.sleep(100L);
        assertFalse(deathNote.writeDeathCause(STD_CAUSE_OF_DEATH));
        assertEquals(CAUSE_OF_DEATH, deathNote.getDeathCause(HUMAN));
    }

    /*
     * After writing the cause of death, details of the death should be written in the next 6 seconds and 40 milliseconds of writing the death's cause.
      * - check that writing the death details before writing a name throws the correct exception
      * - write the name of a human in the notebook
      * - verify that the details of the death are currently empty
      * - set the details of the death to "ran for too long"
      * - verify that death details have been set correctly (returned true, and the details are indeed "ran for too long")
      * - write the name of another human in the notebook
      * - sleep for 6100ms
      * - try to change the details
      * - verify that the details have not been changed
     */
    @Test
    void testDeathDetails() throws InterruptedException {
        try {
            deathNote.writeDetails(DEATH_DETAILS);
            fail("writeDetails() permits to write the death details before writing a name");
        } catch (IllegalStateException e) {
            checkEmptyString(e.getMessage());
        }
        deathNote.writeName(HUMAN);
        assertTrue(deathNote.getDeathDetails(HUMAN).isEmpty());
        assertTrue(deathNote.writeDetails(DEATH_DETAILS));
        assertEquals(DEATH_DETAILS, deathNote.getDeathDetails(HUMAN));
        Thread.sleep(6100L);
        assertFalse(deathNote.writeDetails(DEATH_DETAILS));
        assertEquals(DEATH_DETAILS, deathNote.getDeathDetails(HUMAN));
    }


    private void checkEmptyString(String str) {
        assertNotNull(str);
        assertFalse(str.isEmpty());
        assertFalse(str.isBlank());
    }
}