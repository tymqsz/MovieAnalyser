package com.tymqsz.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import com.tymqsz.sql_server_connection.Connector;

public class MainFrame extends JFrame{
    private Connector connector;
    private FilterVerifier verifier;

    private JPanel mainPanel, recordPanel;
    private JButton searchBtn;
    private JScrollPane scrollPane;
    private JTextArea topText, moviesText, genreText, directorText, maxLengthText;
    private JTextField recordCntField, directorField, maxLengthField;
    private JComboBox<String> genreCB;

    private final Font mainFont = new Font("Calibri", Font.BOLD, 16);
    private final Font resultFont = new Font("Calibri", Font.BOLD, 13);

    public MainFrame(Connector connector, FilterVerifier verifier){
        this.connector = connector;
        this.verifier = verifier;

        setLayout(null);
        setSize(1000, 800);
        setTitle("MovieDB");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        setup();
        setVisible(true);
    }

    private void setup(){
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1000, 800);
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setLayout(null);

        recordPanel = new JPanel();
        recordPanel.setBackground(Color.GRAY);
        recordPanel.setSize(new Dimension(400, 800));
        recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(recordPanel);
        scrollPane.setBounds(300, 300, 400, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        topText = new JTextArea("top");
        topText.setFont(mainFont);
        topText.setBounds(100, 150, 40, 30);
        recordCntField = new JTextField();
        recordCntField.setFont(mainFont);
        recordCntField.setBounds(140, 150, 40, 30);
        moviesText = new JTextArea("movies");
        moviesText.setFont(mainFont);
        moviesText.setBounds(180, 150, 65, 30);

        genreText = new JTextArea("genre: ");
        genreText.setBounds(270, 150, 60, 30);
        genreText.setFont(mainFont);
        String[] genreChoice = {"Drama", "Action", "Comedy", "Romance", "Crime", "%"};
        genreCB = new JComboBox<>(genreChoice);
        genreCB.setBounds(330, 150, 100, 30);

        directorText = new JTextArea("director: ");
        directorText.setFont(mainFont);
        directorText.setBounds(455, 150, 80, 30);
        directorField = new JTextField();
        directorField.setFont(mainFont);
        directorField.setBounds(535, 150, 150, 30);

        maxLengthText = new JTextArea("max length: ");
        maxLengthText.setBounds(710, 150, 110, 30);
        maxLengthText.setFont(mainFont);
        maxLengthField = new JTextField();
        maxLengthField.setFont(mainFont);
        maxLengthField.setBounds(820, 150, 100, 30);

        searchBtn = new JButton("search");
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                loadMovieRecords();
            }
        });
        searchBtn.setBounds(400, 200, 200, 50);
        searchBtn.setBackground(Color.GRAY);

        mainPanel.add(directorText);
        mainPanel.add(directorField);
        mainPanel.add(maxLengthField);
        mainPanel.add(maxLengthText);
        mainPanel.add(topText);
        mainPanel.add(recordCntField);
        mainPanel.add(moviesText);
        mainPanel.add(genreText);
        mainPanel.add(genreCB);
        mainPanel.add(scrollPane);
        mainPanel.add(searchBtn);
        add(mainPanel);
    }

    private void loadMovieRecords(){
        recordPanel.removeAll();

        if(!verifier.countOK(recordCntField.getText()) ||
           !verifier.directorOK(directorField.getText()) ||
           !verifier.lengthOK(maxLengthField.getText())) throw new RuntimeException("WrongInputException");

        int count = Integer.parseInt(recordCntField.getText());
        String genre = (String) genreCB.getSelectedItem();
        String director = directorField.getText().equals("") ? "%" : directorField.getText();
        String max_length = maxLengthField.getText().equals("") ? "1000" : maxLengthField.getText();

        ResultSet results = connector.getTopMovies(count, genre, director, max_length);

        try{
            Dimension size = new Dimension(300, 40);
            while(results.next()){
                JTextField crtResult = new JTextField();
                crtResult.setHorizontalAlignment(JTextField.CENTER);
                crtResult.setEditable(false);
                crtResult.setFont(resultFont);
                crtResult.setPreferredSize(size);

                crtResult.setText(results.getString(1) + ", " + results.getString(2));
                recordPanel.add(crtResult);
            }
            recordPanel.revalidate();
            recordPanel.repaint();
        }
        catch(SQLException e){
            System.out.println(e);
            return;
        }

    }
    
}
