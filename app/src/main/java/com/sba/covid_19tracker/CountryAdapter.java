package com.sba.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    Context context;
    ArrayList<CountryModelClass> CountryList;
    HashMap<String, String> country_decode_list;

    public CountryAdapter(Context context, ArrayList<CountryModelClass> countryList) {
        this.context = context;
        CountryList = countryList;
        country_decode_list = new HashMap<>();
        setHashMap();
    }

    void setHashMap() {
        country_decode_list.put("AFG", "Afghanistan");
        country_decode_list.put("ALA", "Åland Islands");
        country_decode_list.put("ALB", "Albania");
        country_decode_list.put("DZA", "Algeria");
        country_decode_list.put("ASM", "American Samoa");
        country_decode_list.put("AND", "Andorra");
        country_decode_list.put("AGO", "Angola");
        country_decode_list.put("MSZ", "Angola");
        country_decode_list.put("AIA", "Anguilla");
        country_decode_list.put("ATA", "Antarctica");
        country_decode_list.put("ATG", "Antigua and Barbuda");
        country_decode_list.put("ARG", "Argentina");
        country_decode_list.put("ARM", "Armenia");
        country_decode_list.put("ABW", "Aruba");
        country_decode_list.put("AUS", "Australia");
        country_decode_list.put("AUT", "Austria");
        country_decode_list.put("AZE", "Azerbaijan");
        country_decode_list.put("BHS", "Bahamas");
        country_decode_list.put("BHR", "Bahrain");
        country_decode_list.put("BGD", "Bangladesh");
        country_decode_list.put("BRB", "Barbados");
        country_decode_list.put("BLR", "Belarus");
        country_decode_list.put("BEL", "Belgium");
        country_decode_list.put("BLZ", "Belize");
        country_decode_list.put("BEN", "Benin");
        country_decode_list.put("BMU", "Bermuda");
        country_decode_list.put("BTN", "Bhutan");
        country_decode_list.put("BOL", "Bolivia (Plurinational State of)");
        country_decode_list.put("BES", "Bonaire, Sint Eustatius and Saba");
        country_decode_list.put("BIH", "Bosnia and Herzegovina");
        country_decode_list.put("BWA", "Botswana");
        country_decode_list.put("BVT", "Bouvet Island");
        country_decode_list.put("BRA", "Brazil");
        country_decode_list.put("IOT", "British Indian Ocean Territory");
        country_decode_list.put("BRN", "Brunei Darussalam");
        country_decode_list.put("BGR", "Bulgaria");
        country_decode_list.put("BFA", "Burkina Faso");
        country_decode_list.put("BDI", "Burundi");
        country_decode_list.put("CPV", "Cabo Verde");
        country_decode_list.put("KHM", "Cambodia");
        country_decode_list.put("CMR", "Cameroon");
        country_decode_list.put("CAN", "Canada");
        country_decode_list.put("CYM", "Cayman Islands");
        country_decode_list.put("CAF", "Central African Republic");
        country_decode_list.put("TCD", "Chad");
        country_decode_list.put("CHL", "Chile");
        country_decode_list.put("CHN", "China");
        country_decode_list.put("CXR", "Christmas Island");
        country_decode_list.put("CCK", "Cocos (Keeling) Islands");
        country_decode_list.put("COL", "Colombia");
        country_decode_list.put("COM", "Comoros");
        country_decode_list.put("COG", "Congo");
        country_decode_list.put("COD", "Congo, Democratic Republic of the");
        country_decode_list.put("COK", "Cook Islands");
        country_decode_list.put("CRI", "Costa Rica");
        country_decode_list.put("CIV", "Côte d'Ivoire");
        country_decode_list.put("HRV", "Croatia");
        country_decode_list.put("CUB", "Cuba");
        country_decode_list.put("CUW", "Curaçao");
        country_decode_list.put("CYP", "Cyprus");
        country_decode_list.put("CZE", "Czechia");
        country_decode_list.put("DNK", "Denmark");
        country_decode_list.put("DJI", "Djibouti");
        country_decode_list.put("DMA", "Dominica");
        country_decode_list.put("DOM", "Dominican Republic");
        country_decode_list.put("ECU", "Ecuador");
        country_decode_list.put("EGY", "Egypt");
        country_decode_list.put("SLV", "El Salvador");
        country_decode_list.put("GNQ", "Equatorial Guinea");
        country_decode_list.put("ERI", "Eritrea");
        country_decode_list.put("EST", "Estonia");
        country_decode_list.put("SWZ", "Eswatini");
        country_decode_list.put("ETH", "Ethiopia");
        country_decode_list.put("FLK", "Falkland Islands (Malvinas)");
        country_decode_list.put("FRO", "Faroe Islands");
        country_decode_list.put("FJI", "Fiji");
        country_decode_list.put("FIN", "Finland");
        country_decode_list.put("FRA", "France");
        country_decode_list.put("GUF", "French Guiana");
        country_decode_list.put("PYF", "French Polynesia");
        country_decode_list.put("ATF", "French Southern Territories");
        country_decode_list.put("GAB", "Gabon");
        country_decode_list.put("GMB", "Gambia");
        country_decode_list.put("GEO", "Georgia");
        country_decode_list.put("DEU", "Germany");
        country_decode_list.put("GHA", "Ghana");
        country_decode_list.put("GIB", "Gibraltar");
        country_decode_list.put("GRC", "Greece");
        country_decode_list.put("GRL", "Greenland");
        country_decode_list.put("GRD", "Grenada");
        country_decode_list.put("GLP", "Guadeloupe");
        country_decode_list.put("GUM", "Guam");
        country_decode_list.put("GTM", "Guatemala");
        country_decode_list.put("GGY", "Guernsey");
        country_decode_list.put("GIN", "Guinea");
        country_decode_list.put("GNB", "Guinea-Bissau");
        country_decode_list.put("GUY", "Guyana");
        country_decode_list.put("HTI", "Haiti");
        country_decode_list.put("HMD", "Heard Island and McDonald Islands");
        country_decode_list.put("VAT", "Holy See");
        country_decode_list.put("HND", "Honduras");
        country_decode_list.put("HKG", "Hong Kong");
        country_decode_list.put("HUN", "Hungary");
        country_decode_list.put("ISL", "Iceland");
        country_decode_list.put("IND", "India");
        country_decode_list.put("IDN", "Indonesia");
        country_decode_list.put("IRN", "Iran (Islamic Republic of)");
        country_decode_list.put("IRQ", "Iraq");
        country_decode_list.put("IRL", "Ireland");
        country_decode_list.put("IMN", "Isle of Man");
        country_decode_list.put("ISR", "Israel");
        country_decode_list.put("ITA", "Italy");
        country_decode_list.put("JAM", "Jamaica");
        country_decode_list.put("JPN", "Japan");
        country_decode_list.put("JEY", "Jersey");
        country_decode_list.put("JOR", "Jordan");
        country_decode_list.put("KAZ", "Kazakhstan");
        country_decode_list.put("KEN", "Kenya");
        country_decode_list.put("KIR", "Kiribati");
        country_decode_list.put("PRK", "Korea (Democratic People's Republic of)");
        country_decode_list.put("KOR", "Korea, (Republic of) ");
        country_decode_list.put("KWT", "Kuwait");
        country_decode_list.put("KGZ", "Kyrgyzstan");
        country_decode_list.put("LAO", "Lao People's Democratic Republic");
        country_decode_list.put("LVA", "Latvia");
        country_decode_list.put("LBN", "Lebanon");
        country_decode_list.put("LSO", "Lesotho");
        country_decode_list.put("LBR", "Liberia");
        country_decode_list.put("LBY", "Libya");
        country_decode_list.put("LIE", "Liechtenstein");
        country_decode_list.put("LTU", "Lithuania");
        country_decode_list.put("LUX", "Luxembourg");
        country_decode_list.put("MAC", "Macao");
        country_decode_list.put("MDG", "Madagascar");
        country_decode_list.put("MWI", "Malawi");
        country_decode_list.put("MYS", "Malaysia");
        country_decode_list.put("MDV", "Maldives");
        country_decode_list.put("MLI", "Mali");
        country_decode_list.put("MLT", "Malta");
        country_decode_list.put("MHL", "Marshall Islands");
        country_decode_list.put("MTQ", "Martinique");
        country_decode_list.put("MRT", "Mauritania");
        country_decode_list.put("MUS", "Mauritius");
        country_decode_list.put("MYT", "Mayotte");
        country_decode_list.put("MEX", "Mexico");
        country_decode_list.put("FSM", "Micronesia (Federated States of)");
        country_decode_list.put("MDA", "Moldova, Republic of");
        country_decode_list.put("MCO", "Monaco");
        country_decode_list.put("MNG", "Mongolia");
        country_decode_list.put("MNE", "Montenegro");
        country_decode_list.put("MSR", "Montserrat");
        country_decode_list.put("MAR", "Morocco");
        country_decode_list.put("MOZ", "Mozambique");
        country_decode_list.put("MMR", "Myanmar");
        country_decode_list.put("NAM", "Namibia");
        country_decode_list.put("NRU", "Nauru");
        country_decode_list.put("NPL", "Nepal");
        country_decode_list.put("NLD", "Netherlands");
        country_decode_list.put("NCL", "New Caledonia");
        country_decode_list.put("NZL", "New Zealand");
        country_decode_list.put("NIC", "Nicaragua");
        country_decode_list.put("NER", "Niger");
        country_decode_list.put("NGA", "Nigeria");
        country_decode_list.put("NIU", "Niue");
        country_decode_list.put("NFK", "Norfolk Island");
        country_decode_list.put("MKD", "North Macedonia");
        country_decode_list.put("MNP", "Northern Mariana Islands");
        country_decode_list.put("NOR", "Norway");
        country_decode_list.put("OMN", "Oman");
        country_decode_list.put("PAK", "Pakistan");
        country_decode_list.put("PLW", "Palau");
        country_decode_list.put("PSE", "Palestine, State of");
        country_decode_list.put("PAN", "Panama");
        country_decode_list.put("PNG", "Papua New Guinea");
        country_decode_list.put("PRY", "Paraguay");
        country_decode_list.put("PER", "Peru");
        country_decode_list.put("PHL", "Philippines");
        country_decode_list.put("PCN", "Pitcairn");
        country_decode_list.put("POL", "Poland");
        country_decode_list.put("RWA", "Rwanda");
        country_decode_list.put("SHN", "Saint Helena, Ascension and Tristan da Cunha");
        country_decode_list.put("KNA", "Saint Kitts and Nevis");
        country_decode_list.put("LCA", "Saint Lucia");
        country_decode_list.put("MAF", "Saint Martin (French part)");
        country_decode_list.put("SPM", "Saint Pierre and Miquelon");
        country_decode_list.put("VCT", "Saint Vincent and the Grenadines");
        country_decode_list.put("WSM", "Samoa");
        country_decode_list.put("SMR", "San Marino");
        country_decode_list.put("STP", "Sao Tome and Principe");
        country_decode_list.put("SAU", "Saudi Arabia");
        country_decode_list.put("SEN", "Senegal");
        country_decode_list.put("SRB", "Serbia");
        country_decode_list.put("SYC", "Seychelles");
        country_decode_list.put("SLE", "Sierra Leone");
        country_decode_list.put("SGP", "Singapore");
        country_decode_list.put("SXM", "Sint Maarten (Dutch part)");
        country_decode_list.put("SVK", "Slovakia");
        country_decode_list.put("SVN", "Slovenia");
        country_decode_list.put("SLB", "Solomon Islands");
        country_decode_list.put("SOM", "Somalia");
        country_decode_list.put("ZAF", "South Africa");
        country_decode_list.put("SGS", "South Georgia and the South Sandwich Islands");
        country_decode_list.put("SSD", "South Sudan");
        country_decode_list.put("ESP", "Spain");
        country_decode_list.put("LKA", "Sri Lanka");
        country_decode_list.put("SDN", "Sudan");
        country_decode_list.put("SUR", "Suriname");
        country_decode_list.put("SJM", "Svalbard and Jan Mayen");
        country_decode_list.put("SWE", "Sweden");
        country_decode_list.put("CHE", "Switzerland");
        country_decode_list.put("SYR", "Syrian Arab Republic");
        country_decode_list.put("TWN", "Taiwan, Province of China");
        country_decode_list.put("TJK", "Tajikistan");
        country_decode_list.put("TZA", "Tanzania, United Republic of");
        country_decode_list.put("THA", "Thailand");
        country_decode_list.put("TLS", "Timor-Leste");
        country_decode_list.put("TGO", "Togo");
        country_decode_list.put("TKL", "Tokelau");
        country_decode_list.put("TON", "Tonga");
        country_decode_list.put("TTO", "Trinidad and Tobago");
        country_decode_list.put("TUN", "Tunisia");
        country_decode_list.put("TUR", "Turkey");
        country_decode_list.put("TKM", "Turkmenistan");
        country_decode_list.put("TCA", "Turks and Caicos Islands");
        country_decode_list.put("TUV", "Tuvalu");
        country_decode_list.put("UGA", "Uganda");
        country_decode_list.put("UKR", "Ukraine");
        country_decode_list.put("ARE", "United Arab Emirates");
        country_decode_list.put("GBR", "United Kingdom");
        country_decode_list.put("USA", "United States of America");
        country_decode_list.put("UMI", "United States Minor Outlying Islands");
        country_decode_list.put("URY", "Uruguay");
        country_decode_list.put("UZB", "Uzbekistan");
        country_decode_list.put("VUT", "Vanuatu");
        country_decode_list.put("VEN", "Venezuela (Bolivarian Republic of)");
        country_decode_list.put("VNM", "Viet Nam");
        country_decode_list.put("VGB", "Virgin Islands (British)");
        country_decode_list.put("VIR", "Virgin Islands (U.S.)");
        country_decode_list.put("WLF", "Wallis and Futuna");
        country_decode_list.put("ESH", "Western Sahara");
        country_decode_list.put("YEM", "Yemen");
        country_decode_list.put("ZMB", "Zambia");
        country_decode_list.put("ZWE", "Zimbabwe");
        country_decode_list.put("WBG", "West Bank/Gaza Strip");
        country_decode_list.put("RKS", "Kosovo");
        country_decode_list.put("DPS", "Bali");
        country_decode_list.put("QAT", "QATAR");
        country_decode_list.put("ROU", "Romania");
        country_decode_list.put("RUS", "Russian Federation");
        country_decode_list.put("PRT", "Portugal");



    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_list_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        CountryModelClass country = CountryList.get(position);

        holder.country_name.setText(country_decode_list.get(country.getCountryName()));
        holder.confirmed.setText(String.valueOf(country.getConf()));
        holder.recovered.setText(String.valueOf(country.getRec()));
        holder.dead.setText(String.valueOf(country.getDes()));
    }

    @Override
    public int getItemCount() {
        return CountryList.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        public TextView country_name, confirmed, recovered, dead;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            country_name = itemView.findViewById(R.id.country_name);
            confirmed = itemView.findViewById(R.id.country_c);
            recovered = itemView.findViewById(R.id.country_r);
            dead = itemView.findViewById(R.id.country_d);
        }
    }
}
