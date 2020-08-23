package com.wuda.foundation.security;

public enum SubjectPermissionRelationshipCommand {

    GRANT("grant"),
    REVOKE("revoke");

    public String getCommand() {
        return command;
    }

    private String command;

    SubjectPermissionRelationshipCommand(String command) {
        this.command = command;
    }
}
