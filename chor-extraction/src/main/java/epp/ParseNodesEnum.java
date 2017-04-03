package epp;

    public enum ParseNodesEnum {

        PROCESS {
            @Override
            public String toString() {
                return "process";
            }
        },
        TERMINALNODE {
            @Override
            public String toString() {
                return "terminalnode";
            }
        }
    }



