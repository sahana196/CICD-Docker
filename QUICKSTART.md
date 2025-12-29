# Quick Start Guide - CI/CD-Docker Project

## 🚀 Get Your Project Live in 15 Minutes

Follow these steps to deploy your CI/CD-enabled Spring Boot application to the cloud.

---

## Prerequisites Checklist

- [ ] GitHub account
- [ ] Docker Hub account (free)
- [ ] Render account (free) or alternative cloud platform
- [ ] Git installed locally

---

## Step 1: Push to GitHub (5 minutes)

### 1.1 Create GitHub Repository

1. Go to [github.com](https://github.com) and log in
2. Click the **+** icon → **New repository**
3. Repository name: `CICD-Docker` (or your preferred name)
4. Description: `CI/CD-Enabled Spring Boot Application with Docker`
5. Visibility: **Public** (required for free Render deployment)
6. **Do NOT** initialize with README (we already have one)
7. Click **Create repository**

### 1.2 Push Your Code

```bash
# Navigate to your project directory
cd c:\Users\HP\Desktop\CICD-Docker

# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit: Complete CI/CD implementation"

# Add your GitHub repository as remote
# Replace YOUR_USERNAME with your GitHub username
git remote add origin https://github.com/YOUR_USERNAME/CICD-Docker.git

# Push to GitHub
git branch -M main
git push -u origin main
```

✅ **Verify**: Visit your GitHub repository and confirm all files are uploaded.

---

## Step 2: Configure Docker Hub (3 minutes)

### 2.1 Create Docker Hub Account

1. Go to [hub.docker.com](https://hub.docker.com)
2. Sign up for a free account (or log in)

### 2.2 Create Repository

1. Click **Create Repository**
2. Name: `cicd-docker`
3. Visibility: **Public**
4. Click **Create**

### 2.3 Generate Access Token

1. Click your username → **Account Settings**
2. Go to **Security** tab
3. Click **New Access Token**
4. Description: `github-actions-cicd`
5. Access permissions: **Read & Write**
6. Click **Generate**
7. **IMPORTANT**: Copy the token immediately (you won't see it again!)

### 2.4 Add GitHub Secrets

1. Go to your GitHub repository
2. Click **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**

**Add Secret 1:**
- Name: `DOCKERHUB_USERNAME`
- Value: Your Docker Hub username (e.g., `johndoe`)
- Click **Add secret**

**Add Secret 2:**
- Name: `DOCKERHUB_TOKEN`
- Value: Paste the access token you copied
- Click **Add secret**

✅ **Verify**: You should see 2 secrets listed (values are hidden).

---

## Step 3: Deploy to Render (5 minutes)

### 3.1 Create Render Account

1. Go to [render.com](https://render.com)
2. Sign up with GitHub (recommended) or email
3. Verify your email if required

### 3.2 Update render.yaml

1. Open `render.yaml` in your project
2. Replace `YOUR_DOCKERHUB_USERNAME` with your actual Docker Hub username
3. Save the file
4. Commit and push:
   ```bash
   git add render.yaml
   git commit -m "Update Docker Hub username in render.yaml"
   git push
   ```

### 3.3 Deploy Using Blueprint

1. In Render dashboard, click **New +** → **Blueprint**
2. Click **Connect GitHub** (if not already connected)
3. Select your `CICD-Docker` repository
4. Render will detect `render.yaml`
5. Click **Apply**
6. Wait for deployment (2-3 minutes)

### 3.4 Get Deploy Hook (Optional - for auto-deploy)

1. After deployment, click on your service
2. Go to **Settings** → **Deploy Hook**
3. Copy the webhook URL
4. Go to GitHub repository → **Settings** → **Secrets and variables** → **Actions**
5. Add new secret:
   - Name: `RENDER_DEPLOY_HOOK_URL`
   - Value: Paste the webhook URL
   - Click **Add secret**

✅ **Verify**: Your application should now be live!

---

## Step 4: Test Your Live Application (2 minutes)

### 4.1 Get Your Application URL

In Render dashboard, you'll see your app URL (e.g., `https://cicd-docker-app-xxxx.onrender.com`)

### 4.2 Test Endpoints

```bash
# Replace with your actual URL
curl https://your-app.onrender.com/health

# Expected response:
# {"status":"UP","timestamp":1703856234567}

curl https://your-app.onrender.com/config

# Expected response:
# {"message":"Hello from Render Cloud!","envVarPresent":true}
```

Or open in browser:
- `https://your-app.onrender.com/health`
- `https://your-app.onrender.com/config`

✅ **Success!** Your application is live and accessible worldwide! 🎉

---

## Step 5: Test CI/CD Pipeline (3 minutes)

### 5.1 Make a Code Change

Edit `StatusController.java`:

```java
@Value("${APP_MESSAGE:Hello from Automated CI/CD Pipeline!}")
private String appMessage;
```

### 5.2 Commit and Push

```bash
git add .
git commit -m "Update default message"
git push
```

### 5.3 Watch the Pipeline

1. Go to your GitHub repository
2. Click **Actions** tab
3. You'll see your workflow running
4. Click on the workflow to see detailed logs

**Pipeline stages:**
1. ✅ Build & Test (compiles code, runs tests)
2. ✅ Docker Build & Push (creates and uploads Docker image)
3. ✅ Deploy (triggers Render deployment)

### 5.4 Verify Auto-Deployment

Wait 5-7 minutes, then test again:

```bash
curl https://your-app.onrender.com/config
```

You should see your updated message! 🚀

---

## Troubleshooting

### GitHub Actions Fails

**Problem**: Pipeline fails at "Docker Build & Push"

**Solution**:
- Verify `DOCKERHUB_USERNAME` and `DOCKERHUB_TOKEN` secrets are set correctly
- Check Docker Hub token has Read & Write permissions
- Review error logs in GitHub Actions tab

### Render Deployment Fails

**Problem**: Service shows "Deploy failed"

**Solution**:
- Ensure `render.yaml` has correct Docker Hub username
- Verify Docker image exists in Docker Hub
- Check Render logs for specific error messages
- Ensure repository is public on GitHub

### Application Not Responding

**Problem**: URL returns 404 or timeout

**Solution**:
- Wait 2-3 minutes after deployment (cold start)
- Check Render logs for application errors
- Verify health check endpoint is `/health`
- Ensure PORT environment variable is set to 8080

### Tests Fail Locally

**Problem**: `mvn test` fails

**Solution**:
```bash
# Clean and rebuild
mvn clean install

# Check Java version
java -version  # Should be 17+

# Update Maven
mvn -version
```

---

## Environment Variables

You can customize your application by setting environment variables in Render:

1. Go to your service in Render
2. Click **Environment** tab
3. Add variables:
   - `APP_MESSAGE`: Your custom message
   - `JAVA_OPTS`: JVM options (default: `-Xms256m -Xmx512m`)

---

## Alternative Deployment Platforms

### Railway

1. Sign up at [railway.app](https://railway.app)
2. Click **New Project** → **Deploy from GitHub repo**
3. Select your repository
4. Railway auto-detects Dockerfile
5. Add environment variable: `APP_MESSAGE`

### Heroku

```bash
# Install Heroku CLI
heroku login
heroku create your-app-name
heroku stack:set container
git push heroku main
heroku config:set APP_MESSAGE="Hello from Heroku"
```

---

## Next Steps

✅ **Project is live!** Here's what you can do next:

1. **Add Custom Domain**: Configure a custom domain in Render settings
2. **Add Monitoring**: Set up uptime monitoring (e.g., UptimeRobot)
3. **Add More Features**: Extend the API with new endpoints
4. **Add Database**: Integrate PostgreSQL or MongoDB
5. **Add Logging**: Implement structured logging with Logback
6. **Add Metrics**: Add Prometheus metrics endpoint
7. **Add Security**: Implement Spring Security with JWT

---

## Success Checklist

- [ ] Code pushed to GitHub
- [ ] GitHub Actions secrets configured
- [ ] Docker Hub repository created
- [ ] Application deployed to Render
- [ ] Health endpoint accessible
- [ ] Config endpoint returns correct message
- [ ] CI/CD pipeline runs successfully
- [ ] Auto-deployment works on code changes

---

## 🎉 Congratulations!

You now have a fully automated CI/CD pipeline that:

✅ Automatically tests your code  
✅ Builds Docker images  
✅ Deploys to production  
✅ All triggered by a simple `git push`  

**This is production-grade DevOps!** 🚀

---

## Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Docker Hub Documentation](https://docs.docker.com/docker-hub/)
- [Render Documentation](https://render.com/docs)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---

**Need help?** Check the [README.md](file:///c:/Users/HP/Desktop/CICD-Docker/README.md) for detailed documentation.
