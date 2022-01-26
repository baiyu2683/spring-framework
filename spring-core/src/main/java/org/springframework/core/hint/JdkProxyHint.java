/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.hint;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A hint that describes the need of a JDK {@link Proxy}, that is an
 * interfaces-based proxy.
 *
 * @author Stephane Nicoll
 * @since 6.0
 */
public final class JdkProxyHint {

	private final List<TypeReference> proxiedInterfaces;


	private JdkProxyHint(Builder builder) {
		this.proxiedInterfaces = List.copyOf(builder.proxiedInterfaces);
	}

	/**
	 * Return the interfaces to be proxied.
	 * @return the interfaces that the proxy should implement
	 */
	public List<TypeReference> getProxiedInterfaces() {
		return this.proxiedInterfaces;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		JdkProxyHint that = (JdkProxyHint) o;
		return this.proxiedInterfaces.equals(that.proxiedInterfaces);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.proxiedInterfaces);
	}


	/**
	 * Builder for {@link JdkProxyHint}.
	 */
	public static class Builder {

		private final LinkedList<TypeReference> proxiedInterfaces = new LinkedList<>();


		/**
		 * Add the specified interfaces that the proxy should implement.
		 * @param proxiedInterfaces the interfaces the proxy should implement
		 * @return {@code this}, to facilitate method chaining
		 */
		public Builder proxiedInterfaces(TypeReference... proxiedInterfaces) {
			this.proxiedInterfaces.addAll(Arrays.asList(proxiedInterfaces));
			return this;
		}

		/**
		 * Add the specified interfaces that the proxy should implement.
		 * @param proxiedInterfaces the interfaces the proxy should implement
		 * @return {@code this}, to facilitate method chaining
		 */
		public Builder proxiedInterfaces(Class<?>... proxiedInterfaces) {
			this.proxiedInterfaces.addAll(Arrays.stream(proxiedInterfaces)
					.map(TypeReference::of).collect(Collectors.toList()));
			return this;
		}

		/**
		 * Create a {@link JdkProxyHint} based on the state of this builder.
		 * @return a jdk proxy hint
		 */
		public JdkProxyHint build() {
			return new JdkProxyHint(this);
		}

	}

}
