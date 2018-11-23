package nl.utwente.ewi.fmt.EXPRES.expression;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import models.StateSpace;

public class ConstantExpression extends Expression
{
	public final Number value;

	public ConstantExpression(Number val) {
		value = val;
	}

	public Number evaluate(Map<String, ? extends Number> valuation) {
		return value;
	}

	public void writeJani(PrintStream out, int indent) {
		out.print(value);
	}

	public int hashCode() {
		return Long.hashCode(value.longValue());
	}

	public boolean equals(Object other) {
		if (!(other instanceof Expression))
			return false;
		Expression expr = (Expression)other;
		if (!expr.getReferencedVariables().isEmpty())
			return false;
		Number otherV = expr.evaluate(Map.of());
		if (otherV.doubleValue() != value.doubleValue())
			return false;
		return otherV.longValue() == value.longValue();
	}

	public Number evaluate(StateSpace s, int state) {
		return value;
	}

	public String toString() {
		return value.toString();
	}
}
