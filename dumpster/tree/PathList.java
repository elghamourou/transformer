package com.mscc.tree;

import java.util.Iterator;
import java.util.ArrayList;

public class PathList extends ArrayList {

	public PathList() {
		super();
	}

	public PathList(String path) {
		this();
		this.set(path);
	}

	private String render(boolean fromRoot) {
		String result = new String();
		Iterator iter = iterator();
		while (iter.hasNext()) {
			String elem = (String) iter.next();
			if (fromRoot || result.length() > 0) {
				result += "/";
			}
			result += elem;
		}
		return result;
	}

	public String toString() {
		return render(true);
	}

	public String getLast() {
		return (String) this.get(size() - 1);
	}

	public void removeLast() {
		this.remove(size() - 1);
	}

	public void set(String path) {
		this.clear();
		String[] s = path.split("/");
		for (int i = 0; i < s.length; i++) {
			if (s[i].length() > 0)
				this.add(s[i]);
		}
	}

	private static int getCommonLevel(PathList path1, PathList path2) {
		int n = (path1.size() < path2.size() ? path1.size() : path2.size());
		int result = 0;
		for (int i = 0; i < n; i++) {
			String a = (String) path1.get(i);
			String b = (String) path2.get(i);
			if (a.equals(b))
				result++;
			else
				break;
		}
		return result;
	}

	public String getRelativePath(String path) {
		PathList newPath = new PathList();
		newPath.set(path);
		int c = getCommonLevel(this, newPath);

		if (c > 0) {
			PathList pl = new PathList();
			if (this.size() > c) {
				pl.add("ancestor::*[" + (this.size() - c) + "]");
			}
			for (int x = c; x < newPath.size(); x++) {
				pl.add((String) newPath.get(x));
			}
			return pl.render(false);
		} else {
			return path;
		}
	}
}
