/*
 * Copyright 2004-2009 the original author or authors.
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
package org.springframework.core.convert.support;

import java.util.Map;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;

/**
 * Converts a Map to a String collection, where each element in the collection
 * is of the format key=value.
 * @author Keith Donald
 * @since 3.0
 */
@SuppressWarnings("unchecked")
class MapToStringCollection implements ConversionExecutor {

	private MapToStringArray converter;

	private ArrayToCollection collectionConverter;
	
	public MapToStringCollection(TypeDescriptor sourceType, TypeDescriptor targetType, GenericTypeConverter conversionService) {
		converter = new MapToStringArray(sourceType, targetType, conversionService);
		collectionConverter = new ArrayToCollection(TypeDescriptor.valueOf(String[].class), targetType, conversionService);
	}

	public Object execute(Object source) throws ConversionFailedException {
		Map map = (Map) source;
		Object array = converter.execute(map);
		return collectionConverter.execute(array);
	}

}
