package Stream;
import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Stream.StreamIntermediate;

class StreamIntermediateTest {
	@ParameterizedTest
	@MethodSource("negative")
	void testNegative(List<Integer> input, List<Integer> expected) {
		UnaryOperator<Stream<Integer>> op = StreamIntermediate.negative;
		assertThat                   ( op ).isNotNull();

		var         stream = op.apply( input.stream() );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected );
	}
	static Stream<Arguments> negative() {
	    return Stream.of(
	        Arguments.of( List.of(),
                          List.of() ),
	        Arguments.of( List.of(3,4,0),
                          List.of() ),
	        Arguments.of( List.of(-3,-4,-0),
                          List.of(-3,-4) ),
	        Arguments.of( List.of(3,-4,5,-6),
                          List.of(-4,-6) ),
	        Arguments.of( List.of(-1,0,-1,42),
                          List.of(-1,-1) )
	    );
	}
	
	@ParameterizedTest
	@MethodSource("squared")
	void testSquared(List<Integer> input, List<Integer> expected) {
		UnaryOperator<Stream<Integer>> op = StreamIntermediate.squared;
		assertThat                   ( op ).isNotNull();

		var         stream = op.apply( input.stream() );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected );
	}
	static Stream<Arguments> squared() {
	    return Stream.of(
	        Arguments.of( List.of(),
                          List.of() ),
	        Arguments.of( List.of(1,5),
                          List.of(1,25) ),
	        Arguments.of( List.of(0,42,0),
                          List.of(0,1764,0) ),
	        Arguments.of( List.of(-3,2,9,0,11),
                          List.of(9,4,81,0,121) )
	    );
	}
	
	@ParameterizedTest
	@MethodSource("unique")
	void testUnique(List<Character> input, List<Character> expected) {
		UnaryOperator<Stream<Character>> op = StreamIntermediate.unique;
		assertThat                     ( op ).isNotNull();

		var         stream = op.apply( input.stream() );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected );
	}
	static Stream<Arguments> unique() {
	    return Stream.of(
	        Arguments.of( List.of(),
                          List.of() ),
	        Arguments.of( List.of('a','b'),
                          List.of('a','b') ),
	        Arguments.of( List.of('a','c','d','c'),
                          List.of('a','c','d') ),
	        Arguments.of( List.of('a','a','a','a'),
                          List.of('a') ),
	        Arguments.of( List.of('a','b','r','a','c','a','d','a','b','r','a'),
                          List.of('a','b','r','c','d') ),
	        Arguments.of( List.of('s','p','a','c','e'),
                          List.of('s','p','a','c','e') )
	    );
	}
	
	@ParameterizedTest
	@MethodSource("lowercase")
	void testLowercase(String input, List<Character> expected) {
		Function<String,Stream<Character>> op = StreamIntermediate.lowercase;
		assertThat                       ( op ).isNotNull();

		var         stream = op.apply( input );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> lowercase() {
	    return Stream.of(
	        Arguments.of( "AC/DC",
                          List.of() ),
	        Arguments.of( "LowerCase",
                          List.of('o','w','e','r','a','s','e') ),
	        Arguments.of( "aBBa",
                          List.of('a','a') ),
	        Arguments.of( "LoReM IpsUm DoLoR SIT amet,",
                          List.of('o','e','p','s','m','o','o','a','m','e','t') )
	    );
	}

	@ParameterizedTest
	@MethodSource("longest")
	void testLongest(List<String> input, Optional<String> expected) {
		Function<Stream<String>,Optional<String>>  function = StreamIntermediate.longest;
		assertThat                               ( function ).isNotNull();

		var         actual = function.apply( input.stream() );
		assertThat( actual ).isNotNull();
		assertThat( actual ).isEqualTo( expected );
	}
	static Stream<Arguments> longest() {
	    return Stream.of(
		        Arguments.of( List.of(),
	                          Optional.empty() ),
		        Arguments.of( List.of("a","bb","a"),
	                          Optional.of("bb") ),
		        Arguments.of( List.of("Bon","Jovi","living","on","a","prayer"),
	                          Optional.of("living") ),
		        Arguments.of( List.of("","a","b",""),
	                          Optional.of("a") ),
		        Arguments.of( List.of(""),
	                          Optional.of("") )
		    );
	}

	@ParameterizedTest
	@MethodSource("higherThan")
	void testHigherThan(List<Integer> input, Integer number, List<Integer> expected) {
		BiFunction<Stream<Integer>,Integer,Stream<Integer>> function = StreamIntermediate.higherThan;
		assertThat                                        ( function ).isNotNull();

		var         stream = function.apply( input.stream(), number );
		assertThat( stream ).isNotNull();
		var         actual = stream.collect( Collectors.toList() );
		assertThat( actual ).containsExactlyElementsIn( expected ).inOrder();
	}
	static Stream<Arguments> higherThan() {
	    return Stream.of(
		        Arguments.of( List.of(),
		        		      1,
	                          List.of() ),
		        Arguments.of( List.of(1,2,3,4,5),
		        		      0,
	                          List.of(1,2,3,4,5) ),
		        Arguments.of( List.of(1,2,3,4,5),
		        		      3,
	                          List.of(4,5) ),
		        Arguments.of( List.of(5,4,3,2,1),
		        			  5,
	                          List.of() ),
		        Arguments.of( List.of(5,4,3,2,1),
		        		      2,
	                          List.of(5,4,3) )
		    );
	}
}
