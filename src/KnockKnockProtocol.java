/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

public class KnockKnockProtocol {
    private static final int WAITING = 0;
    private static final int ACKNOWLEDGMENTSENT = 1;
    private static final int ACKNOWLEDGED = 2;
    private static final int TERMSNOTACCEPTED = 3;
    private static final int DOWNLOADING = 4;

    private static final int DONE = 5;
    private static final int TERMINATED = 5;

    private int state = WAITING;

    String processInput(String theInput) {
        String theOutput = null;

        switch(state){
            case WAITING:
                theOutput = "Here are the terms of reference. Do you accept? yes or no";
                state = ACKNOWLEDGMENTSENT;
                break;
            case ACKNOWLEDGMENTSENT:
                switch (theInput){
                    case "yes":
                        theOutput = " 1. iTune 2. ZoneAlarm 3. WinRar 4. Audacity Select a resource for downloading\n";
                        state = ACKNOWLEDGED;
                        break;
                    case "no":
                        theOutput = "Enter \"bye\" to exist, and \"enter\" to continue";
                        state = TERMINATED;
                        break;
                    default:
                        theOutput = "\nyou're supposed to enter yes or no";
                        state = ACKNOWLEDGMENTSENT;
                        break;
                }
                break;
            case ACKNOWLEDGED:
                switch (theInput){
                    case "1":
                        theOutput = " You are downloading iTune.zip ...";
                        state = DOWNLOADING;
                        break;
                    case "2":
                        theOutput = " You are downloading ZoneAlarm.zip ...";
                        state = DOWNLOADING;
                        break;
                    case "3":
                        theOutput = " You are downloading WinRar.zip ...";
                        state = DOWNLOADING;
                        break;
                    case "4":
                        theOutput = " You are downloading Audacity.zip ...";
                        state = DOWNLOADING;
                        break;
                    default:
                        theOutput = "you're supposed to enter either 1, 2, 3, or 4";
                        state = ACKNOWLEDGED;
                        break;
                }
                break;
            case DOWNLOADING:
                switch (theInput) {
                    case "<CR>":
                        theOutput = "Enter \"bye\" to exist, and \"enter\" to continue";
                        state = TERMINATED;
                        break;
                    default:
                        theOutput = "Enter \"bye\" to exist, and \"enter\" to continue";
                        state = TERMINATED;
                        break;
                }
                break;
            case TERMINATED:
                switch (theInput) {
                    case "bye":
                        state = DONE;
                        break;
                    case "enter":
                        state = DOWNLOADING;
                        break;
                    default:
                        theOutput = "Enter \"bye\" to exist, or \"enter\" to continue";
                        state = TERMINATED;
                        break;
                }
                break;
        }

        return theOutput;
    }
}
