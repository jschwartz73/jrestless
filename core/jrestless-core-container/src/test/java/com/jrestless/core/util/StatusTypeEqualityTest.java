/*
 * Copyright 2016 Bjoern Bilger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jrestless.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Test;

public class StatusTypeEqualityTest {
	@Test
	public void equals_BothNullGiven_ShouldBeEqual() {
		assertTrue(StatusTypeEquality.isEqual(null, null));
	}

	@Test
	public void equals_SameGiven_ShouldBeEqual() {
		StatusType s1 = mock(StatusType.class);
		assertTrue(StatusTypeEquality.isEqual(s1, s1));
	}

	@Test
	public void equals_FirstNullGiven_ShouldBeUnequal() {
		assertFalse(StatusTypeEquality.isEqual(null, mock(StatusType.class)));
	}

	@Test
	public void equals_SecondNullGiven_ShouldBeUnequal() {
		assertFalse(StatusTypeEquality.isEqual(mock(StatusType.class), null));
	}

	@Test
	public void equals_DiffStatusCodesGiven_ShouldBeUnequal() {
		StatusType s1 = createSt(1, Status.Family.SUCCESSFUL, "reason");
		StatusType s2 = createSt(2, Status.Family.SUCCESSFUL, "reason");
		assertFalse(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void equals_DiffFamiliesGiven_ShouldBeUnequal() {
		StatusType s1 = createSt(1, Status.Family.SUCCESSFUL, "reason");
		StatusType s2 = createSt(1, Status.Family.OTHER, "reason");
		assertFalse(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void equals_FirstNullFamilyGiven_ShouldBeUnequal() {
		StatusType s1 = createSt(1, null, "reason");
		StatusType s2 = createSt(1, Status.Family.SUCCESSFUL, "reason");
		assertFalse(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void equals_SecondNullFamilyGiven_ShouldBeUnequal() {
		StatusType s1 = createSt(1, Status.Family.SUCCESSFUL, "reason");
		StatusType s2 = createSt(1, null, "reason");
		assertFalse(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void equals_BothNullFamiliesGiven_ShouldBeUnequal() {
		StatusType s1 = createSt(1, null, "reason");
		StatusType s2 = createSt(1, null, "reason");
		assertTrue(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void equals_DiffReasonsGiven_ShouldBeUnequal() {
		StatusType s1 = createSt(1, Status.Family.SUCCESSFUL, "reason0");
		StatusType s2 = createSt(1, Status.Family.SUCCESSFUL, "reason1");
		assertFalse(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void equals_FirstNullReasonGiven_ShouldBeUnequal() {
		StatusType s1 = createSt(1, Status.Family.SUCCESSFUL, null);
		StatusType s2 = createSt(1, Status.Family.SUCCESSFUL, "reason");
		assertFalse(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void equals_SecondNullReasonGiven_ShouldBeUnequal() {
		StatusType s1 = createSt(1, Status.Family.SUCCESSFUL, "reason");
		StatusType s2 = createSt(1, Status.Family.SUCCESSFUL, null);
		assertFalse(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void equals_BothNullReasonsGiven_ShouldBeEqual() {
		StatusType s1 = createSt(1, Status.Family.SUCCESSFUL, null);
		StatusType s2 = createSt(1, Status.Family.SUCCESSFUL, null);
		assertTrue(StatusTypeEquality.isEqual(s1, s2));
	}

	@Test
	public void hashCode_NullGiven() {
		assertEquals(0, StatusTypeEquality.hashCode(null));
	}

	@Test
	public void hashCode_CodeGiven() {
		assertEquals(((1) * 31) * 31, StatusTypeEquality.hashCode(createSt(1, null, null)));
	}

	@Test
	public void hashCode_CodeAndFamilyGiven() {
		assertEquals(((1) * 31 + Status.Family.SUCCESSFUL.hashCode()) * 31, StatusTypeEquality.hashCode(createSt(1, Status.Family.SUCCESSFUL, null)));
	}

	@Test
	public void hashCode_CodeAndFamilyAndReasonGiven() {
		assertEquals(((2) * 31 + Status.Family.SUCCESSFUL.hashCode()) * 31 + "reason".hashCode(), StatusTypeEquality.hashCode(createSt(2, Status.Family.SUCCESSFUL, "reason")));
	}

	private StatusType createSt(int code, Status.Family family, String reason) {
		StatusType st = mock(StatusType.class);
		when(st.getStatusCode()).thenReturn(code);
		when(st.getFamily()).thenReturn(family);
		when(st.getReasonPhrase()).thenReturn(reason);
		return st;
	}
}
