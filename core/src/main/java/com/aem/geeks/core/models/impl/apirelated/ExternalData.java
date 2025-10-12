package com.aem.geeks.core.models.impl.apirelated;

//Just a pojo helper class "**This class is written based on the structure of the json**"
public class ExternalData {
    private String id;                          //so these are the fields that are present in our api json "https://jsonplaceholder.typicode.com/users/1"
    private String email;                       //we just create a variables to store the API DATA
    private String name;
    private String userName;
    private String number;
    private String website;
    private Address address;


    public String getId() {
        return id;
    }

    public Address getAddress() {  //from here goes to the ADDRESS class
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getNumber() {
        return number;
    }

    public String getWebsite() {
        return website;
    }

    public class Address{
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;


        public String getStreet() {
            return street;
        }

        public String getSuite() {
            return suite;
        }

        public String getCity() {
            return city;
        }

        public String getZipcode() {
            return zipcode;
        }


        public Geo getGeo() {       //from here goes to the GEO class
            return geo;
        }

        public class Geo {
            private String lat;
            private String lng;

            public String getLng() {
                return lng;
            }

            public String getLat() {
                return lat;
            }
        }
    }
}
