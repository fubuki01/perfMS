package com.hnran.perfmanagesys.utils;

import com.readboy.ssm.po.ContactsPhone;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ContactsPhone> {

	@Override
	public int compare(ContactsPhone lhs, ContactsPhone rhs) {
		return sort(lhs, rhs);
	}

	private int sort(ContactsPhone lhs, ContactsPhone rhs) {
		// 获取ascii值
		int lhs_ascii = lhs.getFistPinyin().toUpperCase().charAt(0);
		int rhs_ascii = rhs.getFistPinyin().toUpperCase().charAt(0);
		// 判断若不是字母，则排在字母之后
		if (lhs_ascii < 65 || lhs_ascii > 90)
			return 1;
		else if (rhs_ascii < 65 || rhs_ascii > 90)
			return -1;
		else
			return lhs.getPinyin().compareTo(rhs.getPinyin());
	}

}
