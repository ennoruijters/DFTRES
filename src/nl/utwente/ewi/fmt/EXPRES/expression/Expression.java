package nl.utwente.ewi.fmt.EXPRES.expression;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import models.StateSpace;

public abstract class Expression
{
	/** Get the set of variables that must be included in the
	 * valuation for the expression to be fully evaluated.
	 */

	public Set<String> getReferencedVariables() {
		return Set.of();
	}

	public Expression simplify(Map<String, ? extends Number> valuation) {
		return this;
	}

	public abstract Number evaluate(Map<String, ? extends Number> valuation);
	public Number evaluate(StateSpace s, int state) {
		HashMap<String, Number> vals = new HashMap<>();
		for (String v : getReferencedVariables()) {
			Number val = s.getVarValue(v, state);
			if (val != null)
				vals.put(v, val);
		}
		return evaluate(vals);
	}

	public static Expression fromJani(Object o)
	{
		if (o instanceof Number)
			return new ConstantExpression((Number)o);
		if (o instanceof String)
			return new VariableExpression((String)o);
		if (o instanceof Boolean)
			return new ConstantExpression(((Boolean)o) ? 1 : 0);
		throw new UnsupportedOperationException("Expression: " + o);
	}

	public Expression booleanExpression()
	{
		return new BinaryExpression(
				BinaryExpression.Operator.NOT_EQUALS,
				this,
				new ConstantExpression(0));
	}

	public abstract int hashCode();
	public abstract boolean equals(Object other);
	public abstract String toString();
	public abstract void writeJani(PrintStream out, int indent);
}