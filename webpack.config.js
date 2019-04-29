var path = require('path');
var webpack = require('webpack');
var BundleTracker = require('webpack-bundle-tracker');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
    context: __dirname,
    entry: ['./assets/js/index.js','./assets/sass/app.scss'],
    output: {
        path: path.resolve('./public/bundles/'),
        filename: 'app.js'
    },
    plugins: [
        new BundleTracker({filename: './webpack-stats.json'}),
        new ExtractTextPlugin({filename: 'app.css', allChunks: true}),
        require('precss'),
        require('autoprefixer')
    ],

    module: {
        rules: [
            {
                test: /\.(css|sass|scss)$/,
                use: ExtractTextPlugin.extract({
                    use: ['css-loader', 'sass-loader']
                })
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.(png|woff|woff2|eot|ttf|svg)$/,
                loader: 'url-loader?limit=100000'
            },
            {
                test: /\.(html)$/,
                loader: 'html-loader'
            }
        ]
    },
    resolve: {
        alias: {vue: 'vue/dist/vue.js'}
    }
};