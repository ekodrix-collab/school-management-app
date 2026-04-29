import * as Localization from 'expo-localization';
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import en from './locales/en.json';
import ml from './locales/ml.json';

const resources = {
    en: { translation: en },
    ml: { translation: ml },
};

i18n
    .use(initReactI18next)
    .init({
        resources,
        // Detect system language, fallback to English
        lng: Localization.getLocales()[0].languageCode === 'ml' ? 'ml' : 'en',
        fallbackLng: 'en',
        interpolation: {
            escapeValue: false, // React already escapes values
        },
    });

export default i18n;