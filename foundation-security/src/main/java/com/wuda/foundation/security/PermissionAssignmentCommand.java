package com.wuda.foundation.security;

public enum PermissionAssignmentCommand {

    GRANT("grant"),
    REVOKE("revoke");

    public String getCommand() {
        return command;
    }

    private String command;

    PermissionAssignmentCommand(String command) {
        this.command = command;
    }
}
