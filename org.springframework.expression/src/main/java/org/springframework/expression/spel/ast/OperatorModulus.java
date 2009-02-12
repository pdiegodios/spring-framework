/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.expression.spel.ast;

import org.antlr.runtime.Token;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Operation;
import org.springframework.expression.spel.ExpressionState;

/**
 * Implements the modulus operator.
 *
 * @author Andy Clement
 * @since 3.0
 */
public class OperatorModulus extends Operator {

	public OperatorModulus(Token payload) {
		super(payload);
	}

	@Override
	public String getOperatorName() {
		return "%";
	}

	@Override
	public Object getValueInternal(ExpressionState state) throws EvaluationException {
		Object operandOne = getLeftOperand().getValueInternal(state);
		Object operandTwo = getRightOperand().getValueInternal(state);
		if (operandOne instanceof Number && operandTwo instanceof Number) {
			Number op1 = (Number) operandOne;
			Number op2 = (Number) operandTwo;
			if (op1 instanceof Double || op2 instanceof Double) {
				return op1.doubleValue() % op2.doubleValue();
			}
			else if (op1 instanceof Float || op2 instanceof Float) {
				return op1.floatValue() % op2.floatValue();
			}
			else if (op1 instanceof Long || op2 instanceof Long) {
				return op1.longValue() % op2.longValue();
			}
			else {
				return op1.intValue() % op2.intValue();
			}
		}
		return state.operate(Operation.MODULUS, operandOne, operandTwo);
	}

}
