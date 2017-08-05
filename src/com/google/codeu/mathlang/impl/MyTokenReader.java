// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
  
package com.google.codeu.mathlang.impl;
  
import java.io.IOException;
  
import com.google.codeu.mathlang.core.tokens.Token;
import com.google.codeu.mathlang.parsing.TokenReader;
import com.google.codeu.mathlang.core.tokens.NameToken;
import com.google.codeu.mathlang.core.tokens.SymbolToken;
import com.google.codeu.mathlang.core.tokens.StringToken;
import com.google.codeu.mathlang.core.tokens.NumberToken;
  
// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {
  
  private String varString;
  private int start=0;
  public MyTokenReader(String source) {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
    varString = source;
  }
  private Boolean isNameToken (String output){
    if (output.contains(" ")){
      return false;
    } 
    if ((output.charAt(0) < 'a' && output.charAt(0) > 'z') || (output.charAt(0) < 'A' && output.charAt(0) > 'Z') ){
      return false;
    }
    return true;
  }
 
  private boolean isSymbolToken(String output){
    if(output.length() != 1){
        return false;
    }
    if((output.charAt(0) >= 'a' && output.charAt(0) <= 'z') || (output.charAt(0) >= 'A' && output.charAt(0) <= 'Z')){
      return false;
    }
    return true;
  }
 
  private boolean isNumberToken(String output){
    for(int i = 0; i < output.length(); i++){
      if(output.charAt(i) < '0' || output.charAt(i) > '9'){
        return false;
      }
    }
    return true;
  }
  
  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.
  
    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.
 
    if(start == varString.length()){
      return null;
    }
 
    while (varString.charAt(start) == ' ' || varString.charAt(start) == '\n'){
      start++;
    }
 
    String token = "";
    int string_flag =0;
    for (int i=start; i<varString.length(); i++){
      if (varString.charAt(i) == ' '){
        start++;
        break;
      } 
      if (varString.charAt(i) == ';'){
        token += varString.charAt(i);
        start++;
        break;
      }
      if (varString.charAt(i) > '0' && varString.charAt(i) < '9'){
        token += varString.charAt(i);
        start++;
        break;
      }
      if (varString.charAt(i) == '\"'){
        string_flag = 1;
        i++;
        start ++;
        while(varString.charAt(i) != '\"'){
          token += varString.charAt(i);
          i++;
          start ++;
        }
        i++;
        start ++;
        break;
      }
      token += varString.charAt(i);
      start ++;
    }
    // System.out.println(start);
    // System.out.println("token: " + token);
 
    if(string_flag == 1){
      string_flag = 0;
      // System.out.println("return string");
      StringToken str = new StringToken(token);
      return str;
    }
    if(isNumberToken(token)){
      // System.out.println("return number");
      NumberToken number = new NumberToken(Double.parseDouble(token));
      return number;
    }
    if(isSymbolToken(token)){
      // System.out.println("return symbol");
      SymbolToken symbol = new SymbolToken(token.charAt(0));
      return symbol;
    }
    if (isNameToken(token)){
      NameToken name = new NameToken(token);
      // System.out.println("return name");
      return name;
    }
    // System.out.println("null");
    return null;
  
  }
}