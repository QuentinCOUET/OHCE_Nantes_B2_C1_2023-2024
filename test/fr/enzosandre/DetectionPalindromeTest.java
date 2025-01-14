package fr.enzosandre;

import fr.enzosandre.test.utilities.VerificationPalindromeBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLOutput;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DetectionPalindromeTest {
    @ParameterizedTest
    @ValueSource(strings = { "test", "epsi" })
    @DisplayName("La chaîne entrée est renvoyée à l'envers")
    public void testMiroir(String chaîne) {
        // ETANT DONNE une chaîne n'étant pas un palindrome
        // QUAND on vérifie si c'est un palindrome
        String résultat = VerificationPalindromeBuilder.Default().Vérifier(chaîne);

        // ALORS on obtient son miroir
        String inversion = new StringBuilder(chaîne)
                .reverse()
                .toString();

        assertTrue(résultat.contains(inversion));
    }

    static Stream<Arguments> casTestPalindrome() {
        return Stream.of(
                Arguments.of(new LangueAnglaise(), Expressions.WellSaid),
                Arguments.of(new LangueFrancaise(), Expressions.BienDit)
        );
    }

    @ParameterizedTest
    @MethodSource("casTestPalindrome")
    @DisplayName("Si la chaîne est un palindrome, on félicite")
    public void testPalindrome(LangueInterface langue, String félicitations) {
        // ETANT DONNE un palindrome
        String palindrome = "radar";

        // ET un utilisateur parlant une <langue>
        var vérificateur = new VerificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérificateur.Vérifier(palindrome);

        // ALORS la chaîne est répétée, suivie de félicitations dans cette langue
        String attendu = palindrome + System.lineSeparator() + félicitations;
        assertTrue(résultat.contains(attendu));
    }

    static Stream<Arguments> casTestBonjour() {
        return Stream.of(
                Arguments.of("test", new LangueFrancaise(), Expressions.Bonjour),
                Arguments.of("radar", new LangueFrancaise(), Expressions.Bonjour),
                Arguments.of("test", new LangueAnglaise(), Expressions.Hello),
                Arguments.of("radar", new LangueAnglaise(), Expressions.Hello)
        );
    }

    @ParameterizedTest
    @MethodSource("casTestBonjour")
    @DisplayName("Avant toute chose, on salue")
    public void testBonjour(String chaîne, LangueInterface langue, String salutations) {
        // ETANT DONNE une chaîne
        // ET un utilisateur parlant une <langue>
        var vérification = new VerificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérification.Vérifier(chaîne);

        // ALORS toute réponse est précédée de <salutations> dans cette <langue>
        String[] lines = résultat.split(System.lineSeparator());
        assertEquals(salutations, lines[0]);
    }

    static Stream<Arguments> casTestAuRevoir() {
        return Stream.of(
                Arguments.of("test", new LangueFrancaise(), Expressions.AuRevoir),
                Arguments.of("radar", new LangueFrancaise(), Expressions.AuRevoir),
                Arguments.of("test", new LangueAnglaise(), Expressions.GoodBye),
                Arguments.of("radar", new LangueAnglaise(), Expressions.GoodBye)
        );
    }

    @ParameterizedTest
    @MethodSource("casTestAuRevoir")
    @DisplayName("Après avoir répondu, on s'acquitte")
    public void testAuRevoir(String chaîne, LangueInterface langue, String salutations) {
        // ETANT DONNE une chaîne
        // ET un utilisateur parlant une <langue>
        var vérification = new VerificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérification.Vérifier(chaîne);

        // ALORS toute réponse est suivie de "Au Revoir"
        String[] lines = résultat.split(System.lineSeparator());
        String lastLine = lines[lines.length - 1];
        assertEquals(salutations, lastLine);
    }

    static Stream<Arguments> casTestBonjourSelonHeure() {
        return Stream.of(
                Arguments.of("test", new LangueFrancaise(), Expressions.Bonjour, MomentDeLaJournée.DepuisHeure(8)),
                Arguments.of("radar", new LangueFrancaise(), Expressions.Bonjour, MomentDeLaJournée.DepuisHeure(11)),
                Arguments.of("test", new LangueFrancaise(), Expressions.Bonjour, MomentDeLaJournée.DepuisHeure(12)),
                Arguments.of("radar", new LangueFrancaise(), Expressions.Bonjour, MomentDeLaJournée.DepuisHeure(17)),
                Arguments.of("test", new LangueFrancaise(), Expressions.Bonsoir, MomentDeLaJournée.DepuisHeure(18)),
                Arguments.of("radar", new LangueFrancaise(), Expressions.Bonsoir, MomentDeLaJournée.DepuisHeure(20)),
                Arguments.of("test", new LangueFrancaise(), Expressions.Bonsoir, MomentDeLaJournée.DepuisHeure(21)),
                Arguments.of("radar", new LangueFrancaise(), Expressions.Bonsoir, MomentDeLaJournée.DepuisHeure(7)),
                Arguments.of("test", new LangueFrancaise(), Expressions.Bonjour, MomentDeLaJournée.Inconnu),
                Arguments.of("radar", new LangueFrancaise(), Expressions.Bonjour, MomentDeLaJournée.Inconnu),
                Arguments.of("test", new LangueAnglaise(), Expressions.GoodMorning, MomentDeLaJournée.DepuisHeure(8)),
                Arguments.of("radar", new LangueAnglaise(), Expressions.GoodMorning, MomentDeLaJournée.DepuisHeure(11)),
                Arguments.of("test", new LangueAnglaise(), Expressions.GoodAfternoon, MomentDeLaJournée.DepuisHeure(12)),
                Arguments.of("radar", new LangueAnglaise(), Expressions.GoodAfternoon, MomentDeLaJournée.DepuisHeure(17)),
                Arguments.of("test", new LangueAnglaise(), Expressions.GoodEvening, MomentDeLaJournée.DepuisHeure(18)),
                Arguments.of("radar", new LangueAnglaise(), Expressions.GoodEvening, MomentDeLaJournée.DepuisHeure(20)),
                Arguments.of("test", new LangueAnglaise(), Expressions.GoodEvening, MomentDeLaJournée.DepuisHeure(21)),
                Arguments.of("radar", new LangueAnglaise(), Expressions.GoodEvening, MomentDeLaJournée.DepuisHeure(7)),
                Arguments.of("test", new LangueAnglaise(), Expressions.Hello, MomentDeLaJournée.Inconnu),
                Arguments.of("radar", new LangueAnglaise(), Expressions.Hello, MomentDeLaJournée.Inconnu)
        );
    }

    @ParameterizedTest
    @MethodSource("casTestBonjourSelonHeure")
    @DisplayName("Avant toute chose, on salue")
    public void testBonjourSelonHeure(String chaîne, LangueInterface langue, String salutations, MomentDeLaJournée momentDeLaJournée) {
        // ETANT DONNE une chaîne
        // ET un utilisateur parlant une <langue>
        // ET selon un moment de la journée
        var vérification = new VerificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .AyantPourMomentDeLaJournée(momentDeLaJournée)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérification.Vérifier(chaîne);

        // ALORS toute réponse est précédée de <salutations> dans cette <langue> en fonction du <momentDeLaJournée>
        String[] lines = résultat.split(System.lineSeparator());
        assertEquals(salutations, lines[0]);

    }


    static Stream<Arguments> testDernierSautLigne() {
        return Stream.of(
                Arguments.of("test", new LangueFrancaise(), Expressions.Bonjour, MomentDeLaJournée.Inconnu),
                Arguments.of("radar", new LangueFrancaise(), Expressions.Bonjour, MomentDeLaJournée.Inconnu),
                Arguments.of("test", new LangueAnglaise(), Expressions.Hello, MomentDeLaJournée.Inconnu),
                Arguments.of("radar", new LangueAnglaise(), Expressions.Hello, MomentDeLaJournée.Inconnu)
        );
    }

    @ParameterizedTest
    @MethodSource("testDernierSautLigne")
    @DisplayName("Le dernier Output est un saut de ligne")
    public void testDernierSautLigne(String chaîne, LangueInterface langue, MomentDeLaJournée momentDeLaJournée) {
        // Récupérer le séparateur de ligne du système
        String newLine = System.getProperty("line.separator");

        // ETANT DONNE une chaîne
        // ET un utilisateur parlant une <langue>
        // ET selon un moment de la journée
        var vérification = new VerificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .AyantPourMomentDeLaJournée(momentDeLaJournée)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérification.Vérifier(chaîne);


        // ALORS on vérifie si le dernier output est un saut de ligne

        assertTrue(résultat.endsWith(System.lineSeparator()));
    }
}
