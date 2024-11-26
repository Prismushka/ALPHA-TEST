package net.minecraft.src;

import java.util.Objects;

public class Pair {
	public Object item0;
	public Object item1;
	public Integer var0;

	public Pair(Object object1, Object object2) {
		this.item0 = object1;
		this.item1 = object2;
	}

	public boolean equals(Object object1) {
		if(this == object1) {
			return true;
		} else if(object1 != null && this.getClass() == object1.getClass()) {
			Pair pair2 = (Pair)object1;
			return Objects.equals(this.item0, pair2.item0) && Objects.equals(this.item1, pair2.item1);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.item0, this.item1});
	}
}
