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

    /**
     * 根据command名称获取.
     *
     * @param command command name
     * @return <code>null</code>-如果没有匹配的
     */
    public static PermissionAssignmentCommand getByCommand(String command) {
        PermissionAssignmentCommand[] commands = PermissionAssignmentCommand.values();
        for (PermissionAssignmentCommand permissionAssignmentCommand : commands) {
            if (command.equalsIgnoreCase(permissionAssignmentCommand.command)) {
                return permissionAssignmentCommand;
            }
        }
        return null;
    }
}
