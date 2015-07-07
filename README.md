# FormDroid
A form engine for android, aim to create a codeless JSON only way of creating android form for data intake from android device. This project is in early development and some of the key component may change in future.

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
      "name": "Are you 18 ?"
    }
  ]
}
```

#License

MIT

