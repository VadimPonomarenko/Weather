package com.example.demo.parser;

import com.example.demo.model.Expectations;
import com.example.demo.model.Weather5Days;
import com.example.demo.model.WeatherDay;
import com.example.demo.model.WeatherHourData;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Parser {
    public Expectations getExpectations() {
        return expectations;
    }
    private Weather5Days data = getDataFromTable();

    private static String getTodayVideo() throws IOException {
        int clouds = getDataFromTable().getDays().get(0).getHours().get(0).getClouds();
        int rain = getDataFromTable().getDays().get(0).getHours().get(0).getRain();
        String video = "";
        if (getDataFromTable().getDays().get(0).getHours().get(0).getWeatherIcon().startsWith("n")) {
            if (rain == 0) {
                video = "video/night.mp4";
            } else {
                video = "video/rainy-night.mp4";
            }
        } else {
            if (rain == 0 && clouds > 1) {
                video = "video/cloudy.mp4";
            } else if (rain == 0 && clouds <= 1) {
                video = "video/sunny-day.mp4";
            } else {
                video = "video/rainy-day.mp4";
            }
        }
        return video;
    }

    private static String getTodayImg() throws IOException {
        int clouds = getDataFromTable().getDays().get(0).getHours().get(0).getClouds();
        int rain = getDataFromTable().getDays().get(0).getHours().get(0).getRain();
        String img = "";
        if (getDataFromTable().getDays().get(0).getHours().get(0).getWeatherIcon().startsWith("n")) {
            if (rain == 0 && clouds > 4) {
                img = "animated/cloudy.svg";
            } else if (rain == 0 && clouds < 2) {
                img = "animated/night.svg";
            } else if (rain == 0 && clouds <= 4) {
                img = "animated/cloudy-night-3.svg";
            } else if (rain == 1) {
                img = "animated/rainy-4.svg";
            } else if (rain == 2) {
                img = "animated/rainy-5.svg";
            } else if (rain == 3) {
                img = "animated/rainy-6.svg";
            } else if (rain > 3) {
                img = "animated/rainy-7.svg";
            } else {
                img = "animated/thunder.svg";
            }
        } else {
            if (rain == 0 && clouds > 4) {
                img = "animated/cloudy.svg";
            } else if (rain == 0 && clouds < 2) {
                img = "animated/day.svg";
            } else if (rain == 0 && clouds <= 4) {
                img = "animated/cloudy-day-3.svg";
            } else if (rain == 1) {
                img = "animated/rainy-4.svg";
            } else if (rain == 2) {
                img = "animated/rainy-5.svg";
            } else if (rain == 3) {
                img = "animated/rainy-6.svg";
            } else if (rain > 3) {
                img = "animated/rainy-7.svg";
            } else {
                img = "animated/thunder.svg";
            }
        }
        return img;
    }


    public Weather5Days getData() {
        return data;
    }
    private String word = getDate();

    public String getWord() {
        return word;
    }

    public Parser() throws IOException {
    }

    private static Document getPage() throws IOException {
        String url = "https://rp5.ru/%D0%9F%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0_%D0%B2_%D0%9F%D0%BE%D0%BA%D1%80%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%BC,_%D0%9D%D0%B5%D0%BA%D0%BB%D0%B8%D0%BD%D0%BE%D0%B2%D1%81%D0%BA%D0%B8%D0%B9_%D1%80%D0%B0%D0%B9%D0%BE%D0%BD";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static String getDate() throws IOException {
        String elem = getPage().select("tr[class=forecastDate]").first().select("span[class=weekDay]").first().text();
        elem = elem.split(",")[0];
        return elem;
    }

    private static String getCurrentGradus() throws IOException {
        String s = getPage().select("div[id=ArchTemp]").first().text();
        String[] array = s.split(" ");
        return array[0];
    }

    private static String getCity() throws IOException {
        String city = getPage().select("div[class=intoLeftNavi]").first().text();
        String[] array = city.split(" ");
        return array[array.length-1];
    }

    private static String[] getTodayExpectations() throws IOException {
        String expectations = getPage().select("div[id=forecastShort-content]").first().text();
        String[] arr = expectations.split("°F");
        String s = arr[0] + arr[2];
        String p = s.split("\\. ")[0];
        String[] k = p.split("°C,");
        String result[] = new String[2];
        result[0] = k[0] + "°C,";
        result[1] = k[1];
        return result;
    }

    public static List<String> getRussianDateInfo() {
        List<String> rusInfo = new ArrayList<>();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        String month = calendar.getDisplayName(Calendar.MONTH,
                Calendar.LONG_FORMAT, new Locale("ru"));
        String[] rusDays = new String[] {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
        DateFormat dateFormat = new SimpleDateFormat("dd.mm HH:mm");
        String dayOfMonth = dateFormat.format(date).split("\\.")[0];
        if (dayOfMonth.startsWith("0")) {
            rusInfo.add(dayOfMonth.substring(1));
        } else {
            rusInfo.add(dayOfMonth);
        }
        rusInfo.add(month);
        rusInfo.add(rusDays[getDayNumberOld(date) - 1]);
        rusInfo.add(dateFormat.format(date).split(" ")[1]);
        return rusInfo;
    }

    public static List<String> getDaysOfWeek() {
        List<String> list = new ArrayList<>();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        String month = calendar.getDisplayName(Calendar.MONTH,
                Calendar.LONG_FORMAT, new Locale("ru"));
        String[] rusDays = new String[] {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
        if (rusDays[getDayNumberOld(date) - 1].equals("Понедельник")) {
            list.add("Воскресенье");
            list.add("Понедельник");
            list.add("Вторник");
            list.add("Среда");
            list.add("Четверг");
        } else if (rusDays[getDayNumberOld(date) - 1].equals("Вторник")) {
            list.add("Понедельник");
            list.add("Вторник");
            list.add("Среда");
            list.add("Четверг");
            list.add("Пятница");
        } else if (rusDays[getDayNumberOld(date) - 1].equals("Среда")) {
            list.add("Вторник");
            list.add("Среда");
            list.add("Четверг");
            list.add("Пятница");
            list.add("Суббота");
        } else if (rusDays[getDayNumberOld(date) - 1].equals("Четверг")) {
            list.add("Среда");
            list.add("Четверг");
            list.add("Пятница");
            list.add("Суббота");
            list.add("Воскресенье");
        } else if (rusDays[getDayNumberOld(date) - 1].equals("Пятница")) {
            list.add("Четверг");
            list.add("Пятница");
            list.add("Суббота");
            list.add("Воскресенье");
            list.add("Понедельник");
        } else if (rusDays[getDayNumberOld(date) - 1].equals("Суббота")) {
            list.add("Пятница");
            list.add("Суббота");
            list.add("Воскресенье");
            list.add("Понедельник");
            list.add("Вторник");
        } else {
            list.add("Суббота");
            list.add("Воскресенье");
            list.add("Понедельник");
            list.add("Вторник");
            list.add("Среда");
        }
        return list;
    }

    private static Weather5Days getDataFromTable() throws IOException {
        Element element = getPage().select("table[id=forecastTable_1_3]").first();
        Elements trs = element.select("tr");
        int row_idx = 0;
        int col_idx = 0;
        int max_col_idx = -1;
        List<String> days = new ArrayList<>();
        List<Integer> hours = new ArrayList<>();
        List<Integer> clouds = new ArrayList<>();
        List<String> cloudNotes = new ArrayList<>();
        List<String> weatherIcon = new ArrayList<>();
        List<Integer> temperature = new ArrayList<>();
        List<Integer> reelFeel = new ArrayList<>();
        List<Integer> windSpeed = new ArrayList<>();
        List<Integer> windMax = new ArrayList<>();
        List<Float> humidity = new ArrayList<>();
        List<Integer> rain = new ArrayList<>();
        List<Float> fogProbability = new ArrayList<>();
        List<String> hidden = new ArrayList<>();
        Date firstDate = new Date();

        firstDate.setHours(0);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Element tr : trs) {
            col_idx = 0;
            Elements tds = tr.select("td");
            if (row_idx == 0) {
                for (Element td : tds) {
                    int colspan = Integer.parseInt(td.attr("colspan"));
                    if (col_idx == 0) {
                        colspan--;
                        String weekDay = td.select("span[class=weekDay]").first().text();
                        String[] weekDayArr = weekDay.split(" ");
                        weekDay = weekDayArr[weekDayArr.length - 2];
                        firstDate.setDate(Integer.parseInt(weekDay));
                        c.setTime(firstDate);
                    }
                    for (int i = 0; i < colspan; i++) {
                        days.add(sdf.format(c.getTime()));
                    }
                    c.add(Calendar.DATE, 1);
                    col_idx++;
                }
            } else if (row_idx == 1) {
                for (Element td : tds) {
                    if (col_idx > 0) {
                        if (td.attr("class").contains("b-right")) {
                            break;
                        }
                        hours.add(Integer.parseInt(td.text()));
                    }
                    col_idx++;
                    max_col_idx = col_idx;
                }
            } else if (row_idx == 2) {
                for (Element td : tds) {
                    if (col_idx > 0 && col_idx < max_col_idx) {
                        Element el = td.select("div[class=cc_0]").first().child(0);
                        clouds.add(Integer.parseInt(el.attr("class").substring(2)));
                        String val = el.attr("onmouseover").split("'<b>")[1].split("' , 'hint'")[0].replace("</b><br/>", " ");
                        cloudNotes.add(val);
                        String forIcon = td.select("div[class=cc_0]").first().child(0).attr("class").substring(1);
                        weatherIcon.add(forIcon);
                    }
                    col_idx++;
                }
            } else if (row_idx == 3) {
                for (Element td : tds) {
                    if (col_idx > 0 && col_idx < max_col_idx) {
                        Element sd = td.select("div[class=pr_0]").first().child(0);
                        String elem = sd.attr("class");
                        String result = elem.substring(elem.length() - 1);
                        String t = td.select("div[class=pr_0]").first().attr("onmouseover").split("\\'")[1];
                        hidden.add(t);
                        rain.add(Integer.parseInt(result));
                    }
                    col_idx++;
                }
            } else if (row_idx == 4) {
                for (Element td : tds) {
                    if (col_idx > 0 && col_idx < max_col_idx) {
                        String e = td.text();
                        if (e.isEmpty()) {
                            fogProbability.add((float) 0);
                        } else {
                            float f = (float) (Integer.parseInt(e)/100.0);
                            fogProbability.add(f);
                        }
                    }
                    col_idx++;
                }
            } else if (row_idx == 5) {
                for (Element td : tds) {
                    if (col_idx > 0 && col_idx < max_col_idx) {
                        String temp = td.select("div").first().text();
                        if (temp.startsWith("+")) {
                            temp = temp.substring(1);
                        } temperature.add(Integer.parseInt(temp));
                    }
                    col_idx++;
                }
            } else if (row_idx == 6) {
                for (Element td : tds) {
                    if (col_idx > 0 && col_idx < max_col_idx) {
                        String reel_feel = td.select("td").first().select("div[class=t_0]").text();
                        if (reel_feel.startsWith("+")) {
                            reel_feel = reel_feel.substring(1);
                        }
                        if (reel_feel.length() > 0) {
                            reelFeel.add(Integer.parseInt(reel_feel));
                        } else {
                            reelFeel.add(0);
                        }
                    }
                    col_idx++;
                }
            } else if (row_idx == 8) {
                for (Element td : tds) {
                    String s = null;
                    if (col_idx > 0 && col_idx < max_col_idx) {
                        int wind_speed = 0;
                        Element wind_elem = td.select("td").first().select("div").first();
                        if (wind_elem != null) {
                            s = wind_elem.text();
                            wind_speed = Integer.parseInt(s);
                        }

                        windSpeed.add(wind_speed);
                    }
                    col_idx++;
                }
            } else if (row_idx == 9) {
                for (Element td : tds) {
                    if (col_idx > 0 && col_idx < max_col_idx) {
                        String wind_max = td.select("td").first().select("div").first().text();
                        if (wind_max.equals("")) {
                            windMax.add(0);
                        } else {
                            windMax.add(Integer.parseInt(wind_max));
                        }
                    }
                    col_idx++;
                }
            } else if (row_idx == 11) {
                for (Element td : tds) {
                    if (col_idx > 0 && col_idx < max_col_idx) {
                        String hum = td.select("b").text();
                        if (hum.equals("")) {
                            hum = td.text();
                        }
                        float res = (float) (Integer.parseInt(hum)/100.0);
                        humidity.add(res);
                    }
                    col_idx++;
                }
            }
            row_idx++;
        }

        List<WeatherDay> weatherDays = new ArrayList<>();
        String lastDate = days.get(0);
        List<WeatherHourData> tempHours = new ArrayList<>();
        for (int i = 0; i < hours.size(); i++) {
            if (!lastDate.equals(days.get(i))) {
                weatherDays.add(new WeatherDay(lastDate, tempHours));
                tempHours = new ArrayList<>();
                lastDate = days.get(i);
            }
            tempHours.add(new WeatherHourData(
                    hours.get(i),
                    temperature.get(i),
                    reelFeel.get(i),
                    clouds.get(i),
                    cloudNotes.get(i),
                    weatherIcon.get(i),
                    rain.get(i),
                    windSpeed.get(i),
                    windMax.get(i),
                    humidity.get(i),
                    fogProbability.get(i),
                    hidden.get(i)
            ));
        }
        weatherDays.add(new WeatherDay(days.get(days.size()-1), tempHours));
        Weather5Days result = new Weather5Days(getCity(), weatherDays);
        return result;
    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    private Expectations expectations = new Expectations(
            getCity(),
            getRussianDateInfo().get(0) + " " + getRussianDateInfo().get(1),
            getDaysOfWeek().get(0),
            getRussianDateInfo().get(3),
            getTodayImg(),
            getTodayVideo(),
            (int)(getData().getDays().get(0).getHours().get(0).getFog_probability() * 100),
            getCurrentGradus(),
            getTodayExpectations()[0],
            getTodayExpectations()[1],
            getDaysOfWeek().get(1),
            getDaysOfWeek().get(2),
            getDaysOfWeek().get(3),
            getDaysOfWeek().get(4)
    );

    public static void main(String[] args) throws IOException {
        getDataFromTable();
    }
}
