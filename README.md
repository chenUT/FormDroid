# FormDroid
A form engine for android, aim to create a codeless JSON only way of creating android form for data intake from android device. The form will be reconfigurable, easy to setup and logic enabled. This project is in early development and some of the key component may change in future.

#Sample App
app folder contain a sample app along with UI testing activites.

#Example
To create a simple form with checkbox and text input form use the following json config

```json
{
  "formId": "sample",
  "fields": [
    {   
      "@type": "text",
      "fieldId": "sampletest",
      "name": "Preferred Name"
    },
    {
      "@type": "checkbox",
      "fieldId": "samplecb",
      "name": "Read and Agree"
    }
  ]
}
```
##Screenshot

![capture](https://cloud.githubusercontent.com/assets/6682969/8553721/47276dba-24b3-11e5-84cd-3a6a35e3cb33.PNG)

#License

MIT

