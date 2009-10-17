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
package org.springframework.mapping.support;

import java.util.LinkedList;
import java.util.List;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.mapping.MappingException;
import org.springframework.mapping.MappingFailure;

final class SpelMappingContext {

	private final EvaluationContext sourceEvaluationContext;

	private final EvaluationContext targetEvaluationContext;

	private final List<MappingFailure> failures = new LinkedList<MappingFailure>();

	public SpelMappingContext(EvaluationContext sourceEvaluationContext, EvaluationContext targetEvaluationContext) {
		this.sourceEvaluationContext = sourceEvaluationContext;
		this.targetEvaluationContext = targetEvaluationContext;
	}

	public Object getSource() {
		return this.sourceEvaluationContext.getRootObject().getValue();
	}
	
	public Object getTarget() {
		return this.targetEvaluationContext.getRootObject().getValue();
	}

	public boolean conditionHolds(Expression condition) {
		if (condition == null) {
			return true;
		}
		return Boolean.TRUE.equals(condition.getValue(this.sourceEvaluationContext));
	}

	public Object getSourceFieldValue(Expression sourceField) {
		return sourceField.getValue(this.sourceEvaluationContext);
	}

	public void setTargetFieldValue(Expression targetField, Object value) {
		targetField.setValue(this.targetEvaluationContext, value);
	}

	public void addMappingFailure(Throwable cause) {
		this.failures.add(new MappingFailure(cause));
	}

	public void handleFailures() {
		if (!this.failures.isEmpty()) {
			throw new MappingException(this.failures);
		}
	}

}
