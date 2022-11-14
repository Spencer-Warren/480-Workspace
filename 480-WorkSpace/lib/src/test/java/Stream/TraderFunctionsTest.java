package Stream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

import Stream.Trader;
import Stream.TraderFunctions;
import Stream.Transaction;

class TraderFunctionsTest {
	private static final Trader JOE   = new Trader(String.valueOf(new char[]{'J','o','e'}),        String.valueOf(new char[]{'L','o','n','d','o','n'}));
	private static final Trader MARIO = new Trader(String.valueOf(new char[]{'M','a','r','i','o'}),String.valueOf(new char[]{'A','r','g','e','n','t','i','n','a'}));
	private static final Trader ALAN  = new Trader(String.valueOf(new char[]{'A','l','a','n'}),    String.valueOf(new char[]{'N','e','w',' ','Y','o','r','k'}));
	private static final Trader BRIAN = new Trader(String.valueOf(new char[]{'B','r','i','a','n'}),String.valueOf(new char[]{'N','e','w',' ','Y','o','r','k'}));

	private static final Transaction A = new Transaction(BRIAN, 2011,  300);
	private static final Transaction B = new Transaction(JOE,   2012, 1000);
	private static final Transaction C = new Transaction(JOE,   2011,  400);
	private static final Transaction D = new Transaction(MARIO, 2012,  710);
	private static final Transaction E = new Transaction(ALAN,  2010,  100);
	private static final Transaction F = new Transaction(BRIAN, 2013,  242);
	private static final Transaction G = new Transaction(MARIO, 2012,  700);
	private static final Transaction H = new Transaction(ALAN,  2012,  950);

	private static final List<Transaction> TRANSACTIONS_NONE = List.of();
	private static final List<Transaction> TRANSACTIONS_ALL  = List.of(A,B,C,D,E,F,G,H);
	private static final List<Transaction> TRANSACTIONS_A    = List.of(A,B,E,G);
	private static final List<Transaction> TRANSACTIONS_B    = List.of(E,F,H);
	private static final List<Transaction> TRANSACTIONS_C    = List.of(A,B,F,G);
	private static final List<Transaction> TRANSACTIONS_D    = List.of(F,G,H);

	@Test
	void testA() {
		Collection<Transaction> actual;
		Collection<Transaction> expected;

		Truth.assertThat( TraderFunctions.a ).isNotNull();
		// (1)
		actual   = TraderFunctions.a.apply( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEmpty();
		// (2)
		actual   = TraderFunctions.a.apply( TRANSACTIONS_ALL );
		expected = List.of(A,C);
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).containsExactlyElementsIn( expected );
		// (3)
		actual   = TraderFunctions.a.apply( TRANSACTIONS_A );
		expected = List.of(A);
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).containsExactlyElementsIn( expected );
	}
	@Test
	void testB() {
		Collection<String> actual;
		Collection<String> expected;

		Truth.assertThat( TraderFunctions.b ).isNotNull();
		// (1)
		actual   = TraderFunctions.b.apply( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEmpty();
		// (2)
		actual   = TraderFunctions.b.apply( TRANSACTIONS_ALL );
		expected = List.of("New York","London","Argentina");
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).containsExactlyElementsIn( expected );
		// (3)
		actual   = TraderFunctions.b.apply( TRANSACTIONS_B );
		expected = List.of("New York");
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).containsExactlyElementsIn( expected );
	}
	@Test
	void testC() {
		Collection<String> actual;
		Collection<String> expected;

		Truth.assertThat( TraderFunctions.c ).isNotNull();
		// (1)
		actual   = TraderFunctions.c.apply( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEmpty();
		// (2)
		actual   = TraderFunctions.c.apply( TRANSACTIONS_ALL );
		expected = List.of("Alan","Brian");
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).containsExactlyElementsIn( expected );
		// (3)
		actual   = TraderFunctions.c.apply( TRANSACTIONS_C );
		expected = List.of("Brian");
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).containsExactlyElementsIn( expected );
	}
	@Test
	void testD() {
		String actual;
		String expected;

		Truth.assertThat( TraderFunctions.d ).isNotNull();
		// (1)
		actual   = TraderFunctions.d.apply( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEmpty();
		// (2)
		actual   = TraderFunctions.d.apply( TRANSACTIONS_ALL );
		expected = "Alan,Brian,Joe,Mario";
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
		// (3)
		actual   = TraderFunctions.d.apply( TRANSACTIONS_B );
		expected = "Alan,Brian";
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
	}
	@Test
	void testE() {
		boolean actual;

		Truth.assertThat( TraderFunctions.e ).isNotNull();
		// (1)
		actual = TraderFunctions.e.test( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isFalse();
		// (2)
		actual = TraderFunctions.e.test( TRANSACTIONS_ALL );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isTrue();
		// (3)
		actual = TraderFunctions.e.test( TRANSACTIONS_A );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isTrue();
		// (4)
		actual = TraderFunctions.e.test( TRANSACTIONS_B );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isFalse();
	}
	@Test
	void testF() {
		int actual;
		int expected;

		Truth.assertThat( TraderFunctions.f ).isNotNull();
		// (1)
		actual   = TraderFunctions.f.applyAsInt( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( 0 );
		// (2)
		actual   = TraderFunctions.f.applyAsInt( TRANSACTIONS_ALL );
		expected = 1410;
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
		// (3)
		actual   = TraderFunctions.f.applyAsInt( TRANSACTIONS_A );
		expected = 700;
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
	}
	@Test
	void testG() {
		OptionalInt actual;
		OptionalInt expected;

		Truth.assertThat( TraderFunctions.g ).isNotNull();
		// (1)
		actual   = TraderFunctions.g.apply( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( OptionalInt.empty() );
		// (2)
		actual   = TraderFunctions.g.apply( TRANSACTIONS_ALL );
		expected = OptionalInt.of( 1000 );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
		// (3)
		actual   = TraderFunctions.g.apply( TRANSACTIONS_B );
		expected = OptionalInt.of( 950 );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );			
	}
	@Test
	void testH() {
		Optional<Transaction> actual;
		Optional<Transaction> expected;

		Truth.assertThat( TraderFunctions.h ).isNotNull();
		// (1)
		actual   = TraderFunctions.h.apply( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( Optional.empty() );
		// (2)
		actual   = TraderFunctions.h.apply( TRANSACTIONS_ALL );
		expected = Optional.of( E );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
		// (3)
		actual   = TraderFunctions.h.apply( TRANSACTIONS_C );
		expected = Optional.of( F );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
	}
	@Test
	void testI() {
		Map<String,Integer> actual;
		Map<String,Integer> expected;

		Truth.assertThat( TraderFunctions.i ).isNotNull();
		// (1)
		actual   = TraderFunctions.i.apply( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEmpty();
		// (2)
		actual   = TraderFunctions.i.apply( TRANSACTIONS_ALL );
		expected = Map.of( "Alan",1050, "Brian",542, "Joe",1400, "Mario",1410 );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
		// (3)
		actual   = TraderFunctions.i.apply( TRANSACTIONS_C );
		expected = Map.of( "Brian",542, "Joe",1000, "Mario",700 );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
	}
	@Test
	void testJ() {
		Map<String,Set<Transaction>> actual;
		Map<String,Set<Transaction>> expected;

		Truth.assertThat( TraderFunctions.j ).isNotNull();
		// (1)
		actual   = TraderFunctions.j.apply( TRANSACTIONS_NONE );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEmpty();
		// (2)
		actual   = TraderFunctions.j.apply( TRANSACTIONS_ALL );
		expected = Map.of( 
				"Argentina",Set.of(D,G), 
				"London",   Set.of(B,C),
				"New York", Set.of(A,E,F,H) );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
		// (3)
		actual   = TraderFunctions.j.apply( TRANSACTIONS_D );
		expected = Map.of( 
				"Argentina",Set.of(G),
				"New York", Set.of(F,H) );
		Truth.assertThat( actual ).isNotNull();
		Truth.assertThat( actual ).isEqualTo( expected );
	}
}
