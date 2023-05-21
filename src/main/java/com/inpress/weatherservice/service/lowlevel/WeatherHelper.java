package com.inpress.weatherservice.service.lowlevel;

import com.inpress.weatherservice.domain.enumaration.WeatherCondition;
import com.inpress.weatherservice.domain.enumaration.WindDirection;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class WeatherHelper {

    public static WeatherCondition fromOWMCode(int code) {
        return switch (code / 100) {
            case 2 -> WeatherCondition.THUNDERSTORM;
            case 3 -> WeatherCondition.DRIZZLE;
            case 5 -> WeatherCondition.RAIN;
            case 6 -> WeatherCondition.SNOW;
            case 7 -> WeatherCondition.ATMOSPHERE;
            case 8 -> switch (code % 100) {
                case 0 -> WeatherCondition.CLEAR;
                default -> WeatherCondition.CLOUDS;
            };
            default -> WeatherCondition.UNKNOWN;
        };
    }

    public static WindDirection fromDirection(Double directionInDegrees) {

        WindDirection cardinalDirection;

        if( (directionInDegrees >= 348.75) && (directionInDegrees <= 360) ||
                (directionInDegrees >= 0) && (directionInDegrees <= 33.75)    ){
            cardinalDirection = WindDirection.NORTH;
        } else if( (directionInDegrees >= 33.75 ) &&(directionInDegrees <= 78.75)){
            cardinalDirection = WindDirection.NORTH_EAST;
        } else if( (directionInDegrees >= 78.75 ) && (directionInDegrees <= 101.25) ){
            cardinalDirection = WindDirection.EAST;
        } else if( (directionInDegrees >= 101.25) && (directionInDegrees <= 146.25) ){
            cardinalDirection = WindDirection.SOUTH_EAST;
        } else if( (directionInDegrees >= 146.25) && (directionInDegrees <= 191.25) ){
            cardinalDirection = WindDirection.SOUTH;
        }  else if( (directionInDegrees >= 191.25) && (directionInDegrees <= 236.25) ){
            cardinalDirection = WindDirection.SOUTH_WEST;
        }  else if( (directionInDegrees >= 236.25) && (directionInDegrees <= 281.25) ){
            cardinalDirection = WindDirection.WEST;
        } else if( (directionInDegrees >= 281.25) && (directionInDegrees <= 348.75) ){
            cardinalDirection = WindDirection.NORTH_WEST;
        } else {
            cardinalDirection = WindDirection.NON;
        }

        return cardinalDirection;
    }
}
