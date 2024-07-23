package com.tymqsz.gui;

import com.tymqsz.sql_server_connection.Connector;

public class Main {
    public static void main(String[] args) {
        Connector connector = new Connector();
        FilterVerifier verifier = new FilterVerifier(connector);
        new MainFrame(connector, verifier);
        
    }
}
