package lettre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

public class GrapheTest {

	private Graphe graphe;

	@Before
	public void setUp() throws LongueursMotsDifferentesException {
		final String[] lesMots = Dicos.dico4;
		graphe = new Graphe(lesMots, 0, 1);
	}

	@Test
	public void testDiffUneLettre() throws LongueursMotsDifferentesException {
		assertTrue(Graphe.diffUneLettre("lion", "lien"));
		assertFalse(Graphe.diffUneLettre("lion", "peur"));
		assertTrue(Graphe.diffUneLettre("peur", "pour"));
	}
	
	@Test(expected=LongueursMotsDifferentesException.class)
	public void testDiffUneLettreException() throws LongueursMotsDifferentesException {
		final String mot1 = "algorithme";
		final String mot2 = "application";
		assertTrue(Graphe.diffUneLettre(mot1, mot2));
	}
	
	@Test
	public void testChemin() {
		final Deque<String> cheminAttendu = new ArrayDeque<String>();
		cheminAttendu.addLast("lion");
		cheminAttendu.addLast("pion");
		cheminAttendu.addLast("paon");
		cheminAttendu.addLast("pain");
		cheminAttendu.addLast("paix");
		cheminAttendu.addLast("poix");
		cheminAttendu.addLast("poux");
		cheminAttendu.addLast("pour");
		cheminAttendu.addLast("peur");
		final Deque<String> cheminObtenu = graphe.chemin("lion", "peur", false);
		while (!cheminAttendu.isEmpty()) {
			assertEquals(cheminAttendu.pop(), cheminObtenu.pop());
		}
	}

}
