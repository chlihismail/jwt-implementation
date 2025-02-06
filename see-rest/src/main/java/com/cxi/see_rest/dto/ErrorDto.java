package com.cxi.see_rest.dto;

public record ErrorDto(
    String message
){
    public static class Builder{
        private String message;
        
        public Builder message(String message){
            this.message = message;
            return this;
        }

        public ErrorDto build(){
            return new ErrorDto(message);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
