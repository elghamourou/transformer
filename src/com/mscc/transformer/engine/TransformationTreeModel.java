package com.mscc.transformer.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class TransformationTreeModel {

	private HashMap<String, List<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>>> tree = new HashMap<>();

	public void addTransformation2(String transformationName, String srcMsg, String dstMsg, String aversion) {

		HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>> srcMsgHash;
		List<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>> srcMsgHashs;
		HashMap<String, List<HashMap<String, String>>> dstMsgHash;
		List<HashMap<String, List<HashMap<String, String>>>> dstMsgHashs;
		HashMap<String, String> version;
		List<HashMap<String, String>> versions;

		if (tree.containsKey(transformationName)) {
			if (tree.get(transformationName) == null)
				tree.put(transformationName, new ArrayList<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>>());
			// tree already contains transformation
			if (getSrcMapWithKey(tree.get(transformationName), srcMsg) != null) {
				if (getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg) == null)
					getSrcMapWithKey(tree.get(transformationName), srcMsg).put(srcMsg,
							new ArrayList<HashMap<String, List<HashMap<String, String>>>>());
				if (getDstMapWithKey(getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg), dstMsg) != null) {
					if (getDstMapWithKey(getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg), dstMsg).get(dstMsg) == null)
						getDstMapWithKey(getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg), dstMsg).put(dstMsg,
								new ArrayList<HashMap<String, String>>());
					if (getVerMapWithKey(getDstMapWithKey(getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg), dstMsg).get(dstMsg),
							aversion) != null) {
						if (getVerMapWithKey(getDstMapWithKey(getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg), dstMsg).get(dstMsg),
								aversion).get(aversion) == null)
							getVerMapWithKey(getDstMapWithKey(getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg), dstMsg).get(dstMsg),
									aversion).put(aversion, transformationName + "/" + srcMsg + "/" + dstMsg + "/" + aversion + "/transformer.xslt");

						versions = getDstMapWithKey(getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg), dstMsg).get(dstMsg);
						version = getVerMapWithKey(versions, aversion);
						if (version != null) {
							version.put(aversion, transformationName + "/" + srcMsg + "/" + dstMsg + "/" + aversion + "/transformer.xslt");
						} else {
							version = new HashMap<String, String>();
							version.put(aversion, transformationName + "/" + srcMsg + "/" + dstMsg + "/" + aversion + "/transformer.xslt");
						}
						versions.add(version);

						dstMsgHashs = getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg);
						dstMsgHash = getDstMapWithKey(dstMsgHashs, dstMsg);
						if (dstMsgHash != null) {
							dstMsgHash.put(dstMsg, versions);
						} else {
							dstMsgHash = new HashMap<String, List<HashMap<String, String>>>();
							dstMsgHash.put(dstMsg, versions);
						}
						dstMsgHashs.add(dstMsgHash);

						srcMsgHashs = tree.get(transformationName);
						srcMsgHash = getSrcMapWithKey(srcMsgHashs, srcMsg);
						if (srcMsgHash != null) {
							srcMsgHash.put(srcMsg, dstMsgHashs);
						} else {
							srcMsgHash = new HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>();
							srcMsgHash.put(srcMsg, dstMsgHashs);
						}
						srcMsgHashs.add(srcMsgHash);
					} else {
						version = new HashMap<String, String>();
						version.put(aversion, transformationName + "/" + srcMsg + "/" + dstMsg + "/" + aversion + "/transformer.xslt");
						versions = new ArrayList<HashMap<String, String>>();
						versions.add(version);

						dstMsgHashs = getSrcMapWithKey(tree.get(transformationName), srcMsg).get(srcMsg);
						dstMsgHash = getDstMapWithKey(dstMsgHashs, dstMsg);
						if (dstMsgHash != null) {
							dstMsgHash.put(dstMsg, versions);
						} else {
							dstMsgHash = new HashMap<String, List<HashMap<String, String>>>();
							dstMsgHash.put(dstMsg, versions);
						}
						dstMsgHashs.add(dstMsgHash);

						srcMsgHashs = tree.get(transformationName);
						srcMsgHash = getSrcMapWithKey(srcMsgHashs, srcMsg);
						if (srcMsgHash != null) {
							srcMsgHash.put(srcMsg, dstMsgHashs);
						} else {
							srcMsgHash = new HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>();
							srcMsgHash.put(srcMsg, dstMsgHashs);
						}
						srcMsgHashs.add(srcMsgHash);
					}

				} else {
					version = new HashMap<String, String>();
					version.put(aversion, transformationName + "/" + srcMsg + "/" + dstMsg + "/" + aversion + "/transformer.xslt");
					versions = new ArrayList<HashMap<String, String>>();
					versions.add(version);

					dstMsgHash = new HashMap<String, List<HashMap<String, String>>>();
					dstMsgHash.put(dstMsg, versions);
					dstMsgHashs = new ArrayList<HashMap<String, List<HashMap<String, String>>>>();
					dstMsgHashs.add(dstMsgHash);

					srcMsgHashs = tree.get(transformationName);
					srcMsgHash = getSrcMapWithKey(srcMsgHashs, srcMsg);
					if (srcMsgHash != null) {
						srcMsgHash.put(srcMsg, dstMsgHashs);
					} else {
						srcMsgHash = new HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>();
						srcMsgHash.put(srcMsg, dstMsgHashs);
					}
					srcMsgHashs.add(srcMsgHash);

				}
			} else {
				version = new HashMap<String, String>();
				version.put(aversion, transformationName + "/" + srcMsg + "/" + dstMsg + "/" + aversion + "/transformer.xslt");
				versions = new ArrayList<HashMap<String, String>>();
				versions.add(version);

				dstMsgHash = new HashMap<String, List<HashMap<String, String>>>();
				dstMsgHash.put(dstMsg, versions);
				dstMsgHashs = new ArrayList<HashMap<String, List<HashMap<String, String>>>>();
				dstMsgHashs.add(dstMsgHash);

				srcMsgHash = new HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>();
				srcMsgHash.put(srcMsg, dstMsgHashs);
				srcMsgHashs = new ArrayList<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>>();
				srcMsgHashs.add(srcMsgHash);

			}
		} else {
			// tree does not contains transformation
			version = new HashMap<String, String>();
			version.put(aversion, transformationName + "/" + srcMsg + "/" + dstMsg + "/" + aversion + "/transformer.xslt");
			versions = new ArrayList<HashMap<String, String>>();
			versions.add(version);

			dstMsgHash = new HashMap<String, List<HashMap<String, String>>>();
			dstMsgHash.put(dstMsg, versions);
			dstMsgHashs = new ArrayList<HashMap<String, List<HashMap<String, String>>>>();
			dstMsgHashs.add(dstMsgHash);

			srcMsgHash = new HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>();
			srcMsgHash.put(srcMsg, dstMsgHashs);
			srcMsgHashs = new ArrayList<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>>();
			srcMsgHashs.add(srcMsgHash);

			tree.put(transformationName, srcMsgHashs);
		}

	}

	private HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>> getSrcMapWithKey(
			List<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>> list, String key) {
		if (list != null) {
			for (HashMap hm : list) {
				if (hm.containsKey(key))
					return hm;
			}
		}
		return null;
	}

	private HashMap<String, List<HashMap<String, String>>> getDstMapWithKey(List<HashMap<String, List<HashMap<String, String>>>> list, String key) {
		if (list != null) {
			for (HashMap hm : list) {
				if (hm.containsKey(key))
					return hm;
			}
		}
		return null;
	}

	private HashMap<String, String> getVerMapWithKey(List<HashMap<String, String>> list, String key) {

		if (list != null) {
			for (HashMap hm : list) {
				if (hm.containsKey(key))
					return hm;
			}
		}

		return null;
	}

	public void addTransformation(String transformationName, String src, String dst, String ver) {
		List<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>> transfoMsgs = tree.get(transformationName);
		if (transfoMsgs == null) {
			transfoMsgs = new ArrayList<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>>();
		}

		HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>> transfoMsg = getSrcMapWithKey(transfoMsgs, src);
		if (transfoMsg == null) {
			transfoMsg = new HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>();
			transfoMsgs.add(transfoMsg);
		}

		List<HashMap<String, List<HashMap<String, String>>>> srcMsgs = transfoMsg.get(src);
		if (srcMsgs == null) {
			srcMsgs = new ArrayList<HashMap<String, List<HashMap<String, String>>>>();
		}

		HashMap<String, List<HashMap<String, String>>> srcMsg = getDstMapWithKey(srcMsgs, dst);
		if (srcMsg == null) {
			srcMsg = new HashMap<String, List<HashMap<String, String>>>();
			srcMsgs.add(srcMsg);
		}

		List<HashMap<String, String>> dstMsgs = srcMsg.get(dst);
		if (dstMsgs == null) {
			dstMsgs = new ArrayList<HashMap<String, String>>();
		}

		HashMap<String, String> dstMsg = getVerMapWithKey(dstMsgs, ver);
		if (dstMsg == null) {
			dstMsg = new HashMap<String, String>();
			dstMsgs.add(dstMsg);
		}

		dstMsg.put(ver, transformationName + "/" + src + "/" + dst + "/" + ver + "/transformer.xslt");
		
		srcMsg.put(dst, dstMsgs);
		
		transfoMsg.put(src, srcMsgs);
		
		tree.put(transformationName, transfoMsgs);
	}

	public String getTransformationXslt(String transformationName, String src, String dst, String ver) {
		List<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>> transfoMsgs = tree.get(transformationName);
		if (transfoMsgs != null) {
			HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>> transfoMsg = getSrcMapWithKey(transfoMsgs, src);
			if (transfoMsg != null) {
				List<HashMap<String, List<HashMap<String, String>>>> srcMsgs = transfoMsg.get(src);
				if (srcMsgs != null) {
					HashMap<String, List<HashMap<String, String>>> srcMsg = getDstMapWithKey(srcMsgs, dst);
					if (srcMsg != null) {
						List<HashMap<String, String>> dstMsgs = srcMsg.get(dst);
						if (dstMsgs != null) {
							HashMap<String, String> dstMsg = getVerMapWithKey(dstMsgs, ver);
							if (dstMsg != null) {
								return dstMsg.get(ver);
							}
						}
					}
				}
			}
		}
		return null;
	}

	public void removeTransformation(String transformationName) {
		List<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>> transfoMsgs = tree.get(transformationName);
		if (transfoMsgs != null) {
			tree.remove(transformationName);
		}

	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		int level = 0;
		String repeated;

		for (Entry<String, List<HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>>>> transfoMsgs : tree.entrySet()) {
			level++;
			repeated = new String(new char[level]).replace("\0", "-");
			buf.append(repeated);
			buf.append(transfoMsgs.getKey());
			buf.append("\n");

			for (HashMap<String, List<HashMap<String, List<HashMap<String, String>>>>> transfoMsg : transfoMsgs.getValue()) {
				level++;

				for (Entry<String, List<HashMap<String, List<HashMap<String, String>>>>> srcMsgs : transfoMsg.entrySet()) {
					level++;
					repeated = new String(new char[level]).replace("\0", "-");
					buf.append(repeated);
					buf.append(srcMsgs.getKey());
					buf.append("\n");

					for (HashMap<String, List<HashMap<String, String>>> srcMsg : srcMsgs.getValue()) {
						level++;

						for (Entry<String, List<HashMap<String, String>>> dstMsgs : srcMsg.entrySet()) {
							level++;
							repeated = new String(new char[level]).replace("\0", "-");
							buf.append(repeated);
							buf.append(dstMsgs.getKey());
							buf.append("\n");

							for (HashMap<String, String> dstMsg : dstMsgs.getValue()) {
								level++;

								for (Entry<String, String> ver : dstMsg.entrySet()) {
									level++;
									repeated = new String(new char[level]).replace("\0", "-");
									buf.append(repeated);
									buf.append(ver.getKey() + "(" + ver.getValue() + ")");
									buf.append("\n");
									level--;
								}

								level--;
							}

							level--;

						}

					}

					level--;
				}

				level--;
			}

			level--;
		}

		return buf.toString();
	}

}
