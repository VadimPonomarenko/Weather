package com.example.demo.model;

import com.example.demo.parser.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilForDays {
    private static Parser parser;

    {
        try {
            parser = new Parser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Weather5Days getDays() throws IOException {
        return parser.getData();
    }
    private static String isToday;

    {
        try {
            isToday = parser.getDate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    List<NeededInfo> list;

    {
        try {
            list = daysInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<NeededInfo> getList() {
        return list;
    }

    public static List<NeededInfo> daysInfo() throws IOException {
        List<NeededInfo> list = new ArrayList<>();

        Weather5Days days = getDays();
        int highestTemp = -500;
        int lowestTemp = 500;
        int rain = 0;
        int clouds = 0;
        double fog = 0.0;
        String img = "";
        String hidden = "";

        if (parser.getWord().startsWith("Сегодня")) {
            for (int j = 1; j < days.getDays().size(); j++) {
                for (int i = 0; i < days.getDays().get(j).getHours().size(); i++) {
                    clouds += days.getDays().get(j).getHours().get(i).getClouds();
                    if (days.getDays().get(j).getHours().get(i).getTemp() < lowestTemp) {
                        lowestTemp = days.getDays().get(j).getHours().get(i).getTemp();
                    }
                    if (days.getDays().get(j).getHours().get(i).getTemp() > highestTemp) {
                        highestTemp = days.getDays().get(j).getHours().get(i).getTemp();
                    }
                    if (days.getDays().get(j).getHours().get(i).getRain() > rain) {
                        rain = days.getDays().get(j).getHours().get(i).getRain();
                    }
                    if (days.getDays().get(j).getHours().get(i).getFog_probability() > fog) {
                        fog = days.getDays().get(j).getHours().get(i).getFog_probability();
                    }
                    if (rain == 0 && clouds > 4) {
                        img = "animated/cloudy.svg";
                        hidden = "Без осадков";
                    } else if (rain == 0 && clouds < 1) {
                        img = "animated/day.svg";
                        hidden = "Без осадков";
                    } else if (rain == 0 && clouds <= 4) {
                        img = "animated/cloudy-day-3.svg";
                        hidden = "Без осадков";
                    } else if (rain == 1) {
                        img = "animated/rainy-4.svg";
                        hidden = "Слабый дождь";
                    } else if (rain == 2) {
                        img = "animated/rainy-5.svg";
                        hidden = "Как из ведра";
                    } else if (rain == 3) {
                        img = "animated/rainy-6.svg";
                        hidden = "Лучше не выходить";
                    } else if (rain > 3) {
                        img = "animated/rainy-7.svg";
                        hidden = "Дождевой апокалипсис";
                    } else {
                        img = "animated/thunder.svg";
                    }
                }
                list.add(new NeededInfo(lowestTemp, highestTemp, rain, (clouds/days.getDays().get(2).getHours().size()), (int)(fog*100), img, hidden));
                highestTemp = -500;
                lowestTemp = 500;
                rain = 0;
                clouds = 0;
                fog = 0;
                img = "";
                hidden = "";
            }
        } else {
            for (int j = 0; j < days.getDays().size(); j++) {
                for (int i = 0; i < days.getDays().get(j).getHours().size(); i++) {
                    clouds += days.getDays().get(j).getHours().get(i).getClouds();
                    hidden = days.getDays().get(j).getHours().get(i).getHidden();
                    if (days.getDays().get(j).getHours().get(i).getTemp() < lowestTemp) {
                        lowestTemp = days.getDays().get(j).getHours().get(i).getTemp();
                    }
                    if (days.getDays().get(j).getHours().get(i).getTemp() > highestTemp) {
                        highestTemp = days.getDays().get(j).getHours().get(i).getTemp();
                    }
                    if (days.getDays().get(j).getHours().get(i).getFog_probability() > fog) {
                        fog = days.getDays().get(j).getHours().get(i).getFog_probability();
                    }
                    if (days.getDays().get(j).getHours().get(i).getRain() > rain) {
                        rain = days.getDays().get(j).getHours().get(i).getRain();
                    }
                    if (rain == 0 && clouds > 4) {
                        img = "animated/cloudy.svg";
                        hidden = "Без осадков";
                    } else if (rain == 0 && clouds < 1) {
                        img = "animated/day.svg";
                        hidden = "Без осадков";
                    } else if (rain == 0 && clouds <= 4) {
                        img = "animated/cloudy-day-3.svg";
                        hidden = "Без осадков";
                    } else if (rain == 1) {
                        img = "animated/rainy-4.svg";
                        hidden = "Слабый дождь";
                    } else if (rain == 2) {
                        img = "animated/rainy-5.svg";
                        hidden = "Как из ведра";
                    } else if (rain == 3) {
                        img = "animated/rainy-6.svg";
                        hidden = "Лучше не выходить";
                    } else if (rain > 3) {
                        img = "animated/rainy-7.svg";
                        hidden = "Дождевой апокалипсис";
                    } else {
                        img = "animated/thunder.svg";
                    }
                }
                list.add(new NeededInfo(lowestTemp, highestTemp, rain, (clouds/days.getDays().get(2).getHours().size()), (int)(fog*100), img, hidden));
                highestTemp = -500;
                lowestTemp = 500;
                rain = 0;
                clouds = 0;
                fog = 0.0;
                img = "";
                hidden = "";
            }
        }


        return list;
    }


    public static void main(String[] args) throws IOException {
    }
}
