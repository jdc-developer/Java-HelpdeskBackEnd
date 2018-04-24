package jdc.helpdesk.enums;

public enum Priority {
	HIGH, NORMAL, LOW;
	
	public static Priority getPriority(String priority) {
		switch(priority.toUpperCase()) {
		case "HIGH": return HIGH;
		case "NORMAL": return NORMAL;
		case "LOW": return LOW;
		default: return null;
		}
	}
}
