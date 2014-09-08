/**
 * This file is part of SensApp Acceptance.
 *
 * SensApp Acceptance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SensApp Acceptance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SensApp Acceptance.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.modelbased.sensapp.util.dbc;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * Helper methods to check pre and post conditions a la 'Eiffel'.
 */
public class Contracts {


    public static <E> void require(E e, Matcher<E> matcher) {
        require(Scope.ARGUMENT, e, matcher);
    }

    public static <E> void require(Scope scope, E e, Matcher<E> matcher) {
        if (!matcher.matches(e)) {
            final Description description = new StringDescription();
            matcher.describeMismatch(e, description);
            final String message = "Precondition violated! " + description.toString();
            switch (scope) {
                case ARGUMENT:
                    throw new IllegalArgumentException(message);
                case STATE:
                    throw new IllegalStateException(message);
                default:
                    throw new RuntimeException("Unexpected scope value: '" + scope.name() + "'");
            }
        }
    }
    
    public static <E> void check(E e, Matcher<E> matcher) {
        assertWithMessage(matcher, e, "Assertion failed:");
    }

    private static <E> void assertWithMessage(Matcher<E> matcher, E e, final String message) {
        final Description description = new StringDescription();
        matcher.describeMismatch(e, description);
        assert matcher.matches(e): message + description.toString();
    }
    
    public static <E> void ensure(E e, Matcher<E> matcher) {
        assertWithMessage(matcher, e, "Postcondition violated: ");
    }
}
